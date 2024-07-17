package vehiclepartspro.support.authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import vehiclepartspro.entities.enumeration.Role;
import vehiclepartspro.support.exception.accountingException.RoleNotFoundException;

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
        Map<String, Object> realmAccess= jwt.getClaim("realm_access");
        List<String> realmRoles = (List<String>) realmAccess.get("roles");
        return realmRoles;

    }

    public static Role getRole() throws RoleNotFoundException
    {
        List<String> roles = getRoles();
        if(roles==null)
        {
            throw new RoleNotFoundException();
        }
        if(roles.contains("Customer"))
        {
            return Role.CUSTOMER;
        }
        else if(roles.contains("Manufacturer"))
        {
            return Role.MANUFACTURER;
        }
        else
        {
            throw new RoleNotFoundException();
        }
    }



}