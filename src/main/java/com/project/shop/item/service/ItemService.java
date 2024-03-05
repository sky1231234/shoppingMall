package com.project.shop.item.service;

import com.project.shop.global.config.Mapper.ItemMapper;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final OptionRepository optionRepository;
    private final ItemImgRepository itemImgRepository;
    private final MemberRepository memberRepository ;
    private final AuthorityRepository authorityRepository ;

    //상품 전체 조회
    public List<ItemListResponse> findAll(){

        return itemRepository.findAll().stream()
                .map(item -> {

                    ItemImg thumbnailItemImg = itemImgRepository.findByItemAndItemImgType(item,ItemImgType.Y)
                            .stream().findFirst()
                            .orElseThrow(()->new RuntimeException("NOT_FOUND_THUMBNAIL"));

                        return ItemListResponse.of(item, thumbnailItemImg);
                    }
                )
                .toList();

    }

    //상품 상세 조회
    public ItemResponse detailFind(long itemId){

        Item item = itemRepository.findById(itemId)
                .orElseThrow(()->new RuntimeException("NOT_FOUND_ITEM"));

        List<Option> option = optionRepository.findByItem(item);

        //상품 메인 이미지
        ItemImgResponse thumbnailItemImg = itemImgRepository.findByItemAndItemImgType(item,ItemImgType.Y)
                                                .stream().findFirst()
                .map(ItemImgResponse::of)
                .orElseThrow(()->new RuntimeException("NOT_FOUND_THUMBNAIL"));

        //나머지 이미지
        List<ItemImgResponse> itemImg = itemImgRepository.findByItemAndItemImgType(item,ItemImgType.N)
                                    .stream()
                                    .map(ItemImgResponse::of)
                                    .toList();

        List<OptionSize> size = option.stream()
                .map(OptionSize::of).toList();

        List<OptionColor> color = option.stream()
                .map(OptionColor::of).toList();

        return ItemResponse.of(item,thumbnailItemImg,itemImg,size,color);

    }

    //상품 등록
    // item + itemImg + option
    @Transactional
    public long create(String loginId, ItemRequest itemRequest){

        authCheck(loginId);

        //category
        Category category = categoryRepository
                .findByCategoryNameAndBrandName(
                        itemRequest.categoryRequest().categoryName(),
                        itemRequest.categoryRequest().brandName())
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_CATEGORY"));

        //item
        var item = itemRequest.toEntity();
        var createItem = itemRepository.save(item.updateCategory(category));

        //itemImg
        List<ItemImg> itemImgList = itemRequest
                .itemImgRequestList()
                .stream()
                .map(x -> {
                    return ItemImg.builder()
                            .item(item)
                            .itemImgType(x.mainImg())
                            .imgUrl(x.url())
                            .insertDate(LocalDateTime.now())
                            .updateDate(LocalDateTime.now())
                            .build();
                })
                .toList();

        itemImgRepository.saveAll(itemImgList);

        //option
        List<Option> optionList = itemRequest
                .optionRequestList()
                .stream()
                .map( x-> {
                    return Option.builder()
                            .item(item)
                            .size(x.size())
                            .color(x.color())
                            .insertDate(LocalDateTime.now())
                            .updateDate(LocalDateTime.now())
                            .build();
                })
                .collect(Collectors.toList());

        optionRepository.saveAll(optionList);

        return createItem.getItemId();
    }

    //상품 수정
    //category + item + itemImg + option
    @Transactional
    public void update(String loginId, long itemId, ItemUpdateRequest itemUpdateRequest){

        authCheck(loginId);

        Category category = categoryRepository
                        .findByCategoryNameAndBrandName(
                                itemUpdateRequest.categoryUpdateRequest().categoryName(),
                                itemUpdateRequest.categoryUpdateRequest().brandName()
                        )
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_CATEGORY"));

        //item
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ITEM"));

        String itemName = itemUpdateRequest.itemName();
        int price = itemUpdateRequest.price();
        String explain = itemUpdateRequest.explain();

        item.updateAfterUpdateItemInfo(category, itemName, price, explain);

        //itemImg
        //기존 이미지 삭제 후 등록
        List<ItemImg> itemImgList = itemImgRepository.findByItem(item);

        if(itemImgList.isEmpty()){
            throw new RuntimeException("NOT_FOUND_ITEM_IMG");
        }
        itemImgRepository.deleteAll(itemImgList);

        List<ItemImg> itemImgUpdateList = itemUpdateRequest
                .itemImgUpdateRequestList()
                .stream()
                .map(x -> {
                    return ItemImg.builder()
                            .item(item)
                            .itemImgType(x.mainImg())
                            .imgUrl(x.url())
                            .insertDate(LocalDateTime.now())
                            .updateDate(LocalDateTime.now())
                            .build();
                })
                .collect(Collectors.toList());

        itemImgRepository.saveAll(itemImgUpdateList);

        //option
        //기존 옵션 삭제 후 등록
        List<Option> optionList = optionRepository.findByItem(item);

        if(optionList.isEmpty())
            throw new RuntimeException("NOT_FOUND_ITEM_OPTION");

        optionRepository.deleteAll(optionList);

        List<Option> optionUpdateList = itemUpdateRequest
                .optionUpdateRequestList()
                .stream()
                .filter(x -> optionRepository.findByItemAndColorAndSize(item,x.color(), x.size()).isEmpty())
                .map(x -> {
                    return Option.builder()
                            .item(item)
                            .color(x.color())
                            .size(x.size())
                            .insertDate(LocalDateTime.now())
                            .updateDate(LocalDateTime.now())
                            .build();
                })
                .collect(Collectors.toList());

        optionRepository.saveAll(optionUpdateList);

    }

    //상품 삭제
    @Transactional
    public void delete(String loginId, long itemId){

        authCheck(loginId);

        Item item = itemRepository.findById(itemId)
                        .orElseThrow(() -> new RuntimeException("NOT_FOUND_ITEM"));

        itemRepository.deleteById(itemId);
        itemImgRepository.deleteByItem(item);
        optionRepository.deleteByItem(item);
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

}
