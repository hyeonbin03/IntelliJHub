package org.zerock.mallapi.dto;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MemberDTO extends User {

    private String email, pw, nicknname;
    private boolean social;

    private List<String> roleNames = new ArrayList<>();

    public MemberDTO(String email, String pw, String nicknname, boolean social, List<String> roleNames){

        super(
                email,
                pw,
                roleNames.stream().map(str -> new SimpleGrantedAuthority("ROLE_"+str)).collect(Collectors.toList()));

                this.email = email;
                this.pw = pw;
                this.nicknname = nicknname;
                this.social = social;
                this.roleNames = roleNames;

    }

    public Map<String, Object> getClaims(){

        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("email",email);
        dataMap.put("pw", pw);
        dataMap.put("nickname", nicknname);
        dataMap.put("social", social);
        dataMap.put("roleNames", roleNames);

        return dataMap;

    }

}
