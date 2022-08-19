package com.ssafy.banggawawo.service;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import com.ssafy.banggawawo.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Service
public class JwtService {

    public static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    private static final String SALT = "banggwawo5ongzol1teamBackEndSecretKey";
    private String secretKey = Base64.getEncoder().encodeToString(SALT.getBytes());
    private static final int EXPIRE_MINUTES = 60;

    public <T> String create(String userType, Object user) {
        byte[] tmp = {115, 115, 97, 102, 121, 83, 101, 99, 114, 101, 116};
        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg","HS256")
                .setHeaderParam("regDate", System.currentTimeMillis())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * EXPIRE_MINUTES))
                .setSubject("userInfo")
                .claim("userType", userType)
                .claim("user", user)
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
        return jwt;
    }

    private byte[] generateKey() {
        byte[] key = null;
        try {
            key = SALT.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            if (logger.isInfoEnabled()) {
                e.printStackTrace();
            } else {
                logger.error("Making JWT Key Error ::: {}", e.getMessage());
            }
        }

        return key;
    }

//    //	전달 받은 토큰이 제대로 생성된것인지 확인 하고 문제가 있다면 UnauthorizedException을 발생.
//    public boolean isUsable(String jwt) {
//        try {
//            System.out.println(jwt);
////            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(jwt);
////            Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(jwt);
//            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(this.generateKey()).build().parseClaimsJws(jwt);
//            return true;
//        } catch (Exception e) {
//
//            logger.error(e.getMessage());
//
//            return false;
//        }
//    }
//
//    public Map<String, Object> passToken(String jwtToken) {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
//                .getRequest();
//        String jwt = request.getHeader("userInfo");
//        Jws<Claims> claims = null;
//        try {
//            claims = Jwts.parser().setSigningKey(SALT.getBytes("UTF-8")).parseClaimsJws(jwtToken);
////            claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(jwt);
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            throw new UnauthorizedException();
//        }
//        Map<String, Object> value = claims.getBody();
//        logger.info("value : {}", value);
//        return value;
//    }

}
