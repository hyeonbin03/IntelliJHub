package org.zerock.mallapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.mallapi.util.CustomJWTException;
import org.zerock.mallapi.util.JWTUtil;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
public class APIRefreshController {

    @RequestMapping("/api/member/refresh")
    public Map<String, Object> refresh(
            @RequestHeader("Authorization") String authHeader,
            String refreshToken
    ) {

        if (refreshToken == null) {
            throw new CustomJWTException("NULL_REFRESH");
        }
        if (authHeader == null || authHeader.length() < 7) {
            throw new CustomJWTException("INVALID STRING");
        }

        //Bearer
        String accessToken = authHeader.substring(7);

        //Access Token의 만료 여부 확인
        if (!checkExpiredToken(accessToken)) {
            return Map.of("accessToken", accessToken, "refreshToken", refreshToken);

        }
        Map<String, Object> claims = JWTUtil.validateToken(refreshToken);
        log.info("refresh........claims : " + claims);

        String newAccessToken = JWTUtil.generateToken(claims, 10);

        String newRefreshToken = checkTime((Integer) claims.get("exp")) ? JWTUtil.generateToken(claims, 60 * 24) : refreshToken;
        return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);

    }

        //down 1
        private boolean checkTime(Integer exp) {
            java.util.Date expDate = new java.util.Date((long) exp * (1000));

            long gap = expDate.getTime() - System.currentTimeMillis();

            long leftMin = gap / (1000*60);

            return leftMin < 60;
        }

        private boolean checkExpiredToken(String token) {

            try {
                JWTUtil.validateToken(token);
            } catch (CustomJWTException ex) {
                if (ex.getMessage().equals("Expired")) {
                    return true;
                }
            }
            return false;
        }
}
