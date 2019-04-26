package com.tsystems.tshop.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.h2.util.IOUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Base64;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ConfirmationSendingService {

    private static final String AUTHORIZATION_HEADER = Base64.getEncoder()
            .encodeToString("d77dk@mail.ru:bg3gqza7qqSsUjxI7CufrIyBcfXn".getBytes());
    private URL link;
    private String CONFIRM_CODE;
    private static final String LINK_URL = "http://gate.smsaero.ru/v2/sms/send?number=79066700552&text=%s&sign=SMS Aero&channel=DIRECT";

    public ConfirmationSendingService() {    }

    private void setCodeToLinkUrl() throws MalformedURLException {
        final String LINK_URL_WIDTH_CODE = String.format(LINK_URL, CONFIRM_CODE);
        this.link = new URL(LINK_URL_WIDTH_CODE);
    }

    private void generateCode(){
        Long code = (new Random(System.currentTimeMillis())).nextLong();
        String string = Long.toString(code);
        CONFIRM_CODE = string.substring(1,6);
        System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQ=============="+ string);
    }

    public String sendConfirmCode() throws IOException {
        generateCode();
        setCodeToLinkUrl();
        final HttpURLConnection urlConnection = (HttpURLConnection) link.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("Authorization", String.format("Basic %s", AUTHORIZATION_HEADER));
        // urlConnection.setInstanceFollowRedirects(true);
        final InputStream inputStream = urlConnection.getInputStream();
        String string = new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining("\n"));
        System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQ+++++++++++++++++"+CONFIRM_CODE);
        return CONFIRM_CODE;
    }

}
