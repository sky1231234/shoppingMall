package com.project.shop.item.service;

import com.project.shop.item.domain.*;
import com.project.shop.item.dto.request.*;
import com.project.shop.item.dto.response.*;
import com.project.shop.item.repository.*;
import com.project.shop.member.domain.Authority;
import com.project.shop.member.domain.Member;
import com.project.shop.member.repository.AuthorityRepository;
import com.project.shop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final OptionRepository optionRepository;
    private final ItemImgRepository itemImgRepository;
    private final MemberRepository memberRepository ;
    private final AuthorityRepository authorityRepository ;

    //상품 전체 조회
    @Override
    public List<ItemListResponse> findAll(){

        return itemRepository.findAll().stream()
                .map(item -> {

                    Thumbnail thumbnailItemImg = getMainImgByItem(item);

                    return ItemListResponse.of(item, thumbnailItemImg);
                    }
                )
                .toList();

    }

    //상품 상세 조회
    @Override
    public ItemResponse detailFind(long itemId){

        Item item = getItemById(itemId);

        //이미지
        Thumbnail thumbnailItemImg = getMainImgByItem(item);
        List<ItemImgResponse> itemImg = getImgByItem(item);

        OptionDetails optionDetails = getOptionByItem(item);

        return ItemResponse.of(item,
                thumbnailItemImg,
                itemImg,
                optionDetails.getOptionSizeList(),
                optionDetails.getOptionColorList());

    }

    //상품 등록
    // item + itemImg + option
    @Transactional
    @Override
    public long create(String loginId, ItemRequest itemRequest){

        authCheck(loginId);

        //category
        CategoryRequest categoryRequest = itemRequest.categoryRequest();
        Category category = getCategory(
                categoryRequest.categoryName(),
                categoryRequest.brandName());

        //item
        Item item = toItem(itemRequest, category);
        Item createItem = createItem(item);

        //itemImg
        createItemImg(itemRequest, item);

        //option
        createOption(itemRequest, item);

        return createItem.getItemId();
    }



    //상품 수정
    //category + item + itemImg + option
    @Transactional
    @Override
    public void update(String loginId, long itemId, ItemUpdateRequest itemUpdateRequest){

        authCheck(loginId);

        CategoryUpdateRequest categoryRequest = itemUpdateRequest.categoryUpdateRequest();
        Category category = getCategory(
                categoryRequest.categoryName(),
                categoryRequest.brandName());

        //item
        Item item = getItemById(itemId);
        updateItem(itemId, itemUpdateRequest, category);

        // 고치기 : updateItemImg와 updateItemOption는 중복되는 부분이 많은데
        // 합치면 제네릭으로? 이런것도 합치는게 좋은지
        //itemImg
        updateItemImg(item, itemUpdateRequest);

        //option
        updateItemOption(item,itemUpdateRequest);
    }

    //상품 삭제
    @Transactional
    @Override
    public void delete(String loginId, long itemId){

        authCheck(loginId);

        Item item = getItemById(itemId);

        deleteItemById(itemId);
        deleteItemData(item);
    }

    //admin 권한 확인
    private void authCheck(String loginId){

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_MEMBER"));
        Authority authority = authorityRepository.findByMember(member)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_AUTH"));

        if(authority.getAuthName().equals("user"))
            throw new RuntimeException("ONLY_ADMIN");
    }

    private Category getCategory(String categoryName, String brandName) {
        return categoryRepository
                .findByCategoryNameAndBrandName(categoryName, brandName)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_CATEGORY"));

    }

    private Item toItem(ItemRequest itemRequest, Category category) {
        return itemRequest.toEntity(category);
    }

    @Transactional
    private Item createItem(Item item) {
        return itemRepository.save(item);
    }

    @Transactional
    private void createItemImg(ItemRequest itemRequest, Item item){

        List<ItemImg> itemImgList = itemRequest.itemImgRequestList()
                .stream()
                .map(ImgRequest -> ImgRequest.toEntity(item))
                .toList();

        itemImgRepository.saveAll(itemImgList);
    }

    @Transactional
    private void createOption(ItemRequest itemRequest, Item item){

        List<Option> optionList = itemRequest.optionRequestList()
                .stream()
                .map(OptionRequest -> OptionRequest.toEntity(item))
                .toList();

        optionRepository.saveAll(optionList);
    }

    private void updateItem(long itemId, ItemUpdateRequest itemUpdateRequest, Category category){
        Item item = getItemById(itemId);

        item.updateItemInfo(category,
                itemUpdateRequest.itemName(),
                itemUpdateRequest.price(),
                itemUpdateRequest.explain());
    }

    private Item getItemById(long itemId){
         return itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ITEM"));
    }

    private void updateItemImg(Item item, ItemUpdateRequest itemUpdateRequest){

        //기존 이미지 삭제 후 등록
        deleteItemImgByItemIfNotEmpty(item);

        //고치기 : createItemImg와 메소드 합치고싶음
        createItemImgForUpdate(item, itemUpdateRequest);

    }

    private void deleteItemImgByItemIfNotEmpty(Item item){
        List<ItemImg> itemImgList = itemImgRepository.findByItem(item);
        if (!itemImgList.isEmpty()) {
            itemImgRepository.deleteAll(itemImgList);
        }
    }

    private void createItemImgForUpdate(Item item, ItemUpdateRequest itemUpdateRequest){

        List<ItemImg> itemImgUpdateList = itemUpdateRequest
                .itemImgUpdateRequestList()
                .stream()
                .map(ImgUpdateRequest -> ImgUpdateRequest.toEntity(item))
                .toList();

        itemImgRepository.saveAll(itemImgUpdateList);
    }


    //기존 옵션 삭제 후 등록
    private void updateItemOption(Item item, ItemUpdateRequest itemUpdateRequest){

        deleteItemOptionByItemIfNotEmpty(item);
        createItemOptionForUpdate(item, itemUpdateRequest);

    }

    private void deleteItemOptionByItemIfNotEmpty(Item item){
        List<Option> optionList = optionRepository.findByItem(item);
        if (!optionList.isEmpty()) {
            optionRepository.deleteAll(optionList);
        }
    }

    private void createItemOptionForUpdate(Item item, ItemUpdateRequest itemUpdateRequest){

        List<Option> optionUpdateList = itemUpdateRequest
                .optionUpdateRequestList()
                .stream()
                .filter(option -> optionRepository.findByItemAndColorAndSize(item,option.color(), option.size()).isEmpty())
                .map(OptionUpdateRequest -> OptionUpdateRequest.toEntity(item))
                .collect(Collectors.toList());

        optionRepository.saveAll(optionUpdateList);

    }

    private void deleteItemById(long itemId){
        itemRepository.deleteById(itemId);
    }

    private void deleteItemData(Item item){
        deleteItemImgByItemIfNotEmpty(item);
        deleteItemOptionByItemIfNotEmpty(item);
    }


    //메인 이미지와 나머지 이미지를 가져오는 부분이 거의 비슷함
    private Thumbnail getMainImgByItem(Item item){

        return itemImgRepository.findByItemAndItemImgType(item,ItemImgType.Y)
                .stream().findFirst()
                .map(Thumbnail::of)
                .orElseThrow(()->new RuntimeException("NOT_FOUND_THUMBNAIL"));

    }

    private List<ItemImgResponse> getImgByItem(Item item){
        return itemImgRepository.findByItemAndItemImgType(item,ItemImgType.N)
                .stream()
                .map(ItemImgResponse::of)
                .toList();
    }

    private OptionDetails getOptionByItem(Item item){

        List<Option> option = optionRepository.findByItem(item);

        List<OptionSize> size = getOptionSizeByItem(option);
        List<OptionColor> color = getOptionColorByItem(option);

        return new OptionDetails(size, color);
    }

    private List<OptionSize> getOptionSizeByItem(List<Option> option){

         return option.stream()
                .map(OptionSize::of).toList();
    }

    private List<OptionColor> getOptionColorByItem(List<Option> option){

        return option.stream()
                .map(OptionColor::of).toList();
    }
}
