package vehiclepartspro.support.authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import vehiclepartspro.entities.enumeration.Role;

import javax.management.relation.RoleNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Utils
{

    public static String getEmail() {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authenticationToken.getCredentials();
        String email = (String) jwt.getClaims().get("email");
        return email;
    }

    private static List<String> getRoles()
    {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authenticationToken.getCredentials();
        Map<String, Object> map= jwt.getClaim("resource_access");
        Map<String,Object> User_VPP= (Map<String, Object>) map.get("User_VPP_id");
        //per come fatta l'applicazione uno puo avere un singolo ruolo
        //restituisce quindi una lista costituita da un singolo elemento
        List<String> roles=(List<String>) User_VPP.get("roles");
        return roles;
    }

    public static Role getRole() throws RoleNotFoundException
    {
        List<String> roles = getRoles();
        if(roles==null)
        {
            throw new RoleNotFoundException();
        }
        String role = roles.getFirst();
        if(role.equals("Manufacturer"))
        {
            return Role.MANUFACTURER;
        }
        else if(role.equals("Customer"))
        {
            return Role.CUSTOMER;
        }
        else
        {
            throw new RoleNotFoundException();
        }
    }



}