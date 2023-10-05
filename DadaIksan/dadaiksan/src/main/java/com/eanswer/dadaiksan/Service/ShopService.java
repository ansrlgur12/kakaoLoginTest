package com.eanswer.dadaiksan.Service;

import com.eanswer.dadaiksan.Dto.EventDto;
import com.eanswer.dadaiksan.Dto.ShopDto;
import com.eanswer.dadaiksan.Entity.Event;
import com.eanswer.dadaiksan.Entity.Shop;
import com.eanswer.dadaiksan.Repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService {

    @Autowired
    private AuthService authService;

    @Autowired
    private ShopRepository shopRepository;
    private ShopDto shopDto;

    public boolean newShop(ShopDto shopDto, HttpServletRequest request, UserDetails userDetails) {
        authService.validateTokenAndGetUser(request, userDetails);

        Shop shop = new Shop();

        shop.setShopName(shopDto.getShopName());
        shop.setShopDesc(shopDto.getShopDesc());
        shop.setAddress(shopDto.getAddress());
        shop.setCategory(shopDto.getCategory());
        Shop saveShop = shopRepository.save(shop);
        return saveShop != null;
    }

    @Transactional
    public boolean updateShop(Long id, ShopDto shopDto, HttpServletRequest request, UserDetails userDetails) {
        authService.validateTokenAndGetUser(request, userDetails);

        Shop shop = shopRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 매장이 없습니다."));

        shop.setShopName(shopDto.getShopName());
        shop.setShopDesc(shopDto.getShopDesc());
        shop.setAddress(shopDto.getAddress());
        shop.setCategory(shopDto.getCategory());
        Shop updateShop = shopRepository.save(shop);

        return updateShop != null;
    }

    @Transactional
    public boolean deleteShop(Long id, HttpServletRequest request, UserDetails userDetails) {

        authService.validateTokenAndGetUser(request, userDetails);

        Shop shop = shopRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 매장이 없습니다."));
        shopRepository.delete(shop);

        return true;
    }

    public List<ShopDto> getAllShop() {

        List<ShopDto> shopDtos = new ArrayList<>();
        List<Shop> shops = shopRepository.findAll();

        for (Shop shop : shops) {
            ShopDto shopDto = new ShopDto();

            shopDto.setId(shop.getId());
            shopDto.setShopName(shop.getShopName());
            shopDto.setShopDesc(shop.getShopDesc());
            shopDto.setAddress(shop.getAddress());
            shopDto.setCategory(shop.getCategory());
            shopDtos.add(shopDto);
        }

        return shopDtos;
    }

    public ShopDto readShop(Long id) {

        Shop shop = shopRepository.findById(id).orElseThrow(()->new RuntimeException("매장이 없습니다."));

        ShopDto shopDto = new ShopDto();

        shopDto.setId(shop.getId());
        shopDto.setShopName(shop.getShopName());
        shopDto.setShopDesc(shop.getShopDesc());
        shopDto.setAddress(shop.getAddress());
        shopDto.setCategory(shop.getCategory());

        return shopDto;
    }
}
