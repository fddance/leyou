package com.leyou.auth;
import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.auth.utils.RsaUtils;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

import static org.junit.Assert.*;

public class JwtTest {

    private static final String pubKeyPath = "E:\\heima\\ssh\\ssh.pub";

    private static final String priKeyPath = "E:\\heima\\ssh\\ssh.pri";

    private PrivateKey privateKey;

    private PublicKey publicKey;

    @Test
    public void method1() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "haha");
    }

    @Test
    public void method2() throws Exception {
        PrivateKey privateKey = RsaUtils.getPrivateKey(priKeyPath);
        System.out.println("privateKey = " + privateKey);
        PublicKey publicKey = RsaUtils.getPublicKey(pubKeyPath);
        System.out.println("publicKey = " + publicKey);
    }

    @Test
    public void method3() throws Exception {
        String jacck = JwtUtils.generateToken(new UserInfo(1l, "jacck"), RsaUtils.getPrivateKey(priKeyPath), 5);
        System.out.println("jacck = " + jacck);
    }

    @Test
    public void method4() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJqYWNjayIsImV4cCI6MTU0Mjg4OTQ1MX0.nX1-SoWRuIlelFSgccbo19JtwVd0DYlpEM91bAWLmxgwrS5zfCF9clv8ELEKmc57gRMVGF6zkTStyg1KKDT4H9mxqVIkF26G4te-jiECE_CfkyzKlrAlZ1P8iSVwliXyLieyynvLS7hKtn-PtB7aqJHsG0eVtVwY9k55J52PfMU";
        UserInfo infoFromToken = JwtUtils.getInfoFromToken(token, RsaUtils.getPublicKey(pubKeyPath));
        System.out.println("infoFromToken = " + infoFromToken);
    }

}