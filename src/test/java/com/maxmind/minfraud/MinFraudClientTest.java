package com.maxmind.minfraud;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.maxmind.minfraud.exception.MinFraudException;
import com.maxmind.minfraud.input.*;
import com.maxmind.minfraud.input.Device.Builder;
import com.maxmind.minfraud.input.Event.Type;
import com.maxmind.minfraud.input.Payment.Processor;
import com.maxmind.minfraud.input.Shipping.DeliverySpeed;
import com.maxmind.minfraud.output.Insights;
import com.maxmind.minfraud.output.Score;
import org.json.JSONException;
import org.junit.Rule;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class MinFraudClientTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(0); // 0 picks random port

    @Test
    public void fullScoreRequest() throws Exception {
        String responseContent = this.readJsonFile("score-response");
        MinFraudClient client = this.createClient("score", responseContent);
        Score response = client.score(this.createFullRequest());

        JSONAssert.assertEquals(responseContent, response.toJson(), true);
        this.verifyRequestFor("score");
    }

    @Test
    public void fullInsightsRequest() throws Exception {
        String responseContent = this.readJsonFile("insights-response");
        MinFraudClient client = this.createClient("insights", responseContent);
        Insights response = client.insights(this.createFullRequest());

        // We use non-strict checking as there is some extra stuff in the serialized
        // object, most notably the "name" field in the GeoIP2 Insights subobjects.
        // We cannot change this as it would be a breaking change to the GeoIP2 API.
        JSONAssert.assertEquals(responseContent, response.toJson(), false);
        this.verifyRequestFor("insights");
    }

    public MinFraudClient createClient(String service, String responseContent) throws IOException, MinFraudException {

        stubFor(post(urlEqualTo("/minfraud/v2.0/" + service))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", " application/vnd.maxmind.com-minfraud-" + service + "+json; charset=UTF-8; version=2.0\n")
                                // This is wrong if we use non-ASCII characters, but we don't currently
                        .withHeader("Content-Length", Integer.toString(responseContent.length()))
                        .withBody(responseContent)));

        return new MinFraudClient.Builder(6, "0123456789")
                .host("localhost")
                .port(this.wireMockRule.port())
                .disableHttps()
                .build();
    }

    public MinFraudRequest createFullRequest() throws UnknownHostException, ParseException, URISyntaxException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        return new MinFraudRequest.Builder()
                .event(
                        new Event
                                .Builder()
                                .transactionId("txn3134133")
                                .shopId("s2123")
                                .time(dateFormat.parse("2012-04-12T23:20:50.52Z"))
                                .type(Type.PURCHASE)
                                .build()
                )
                .account(
                        new Account.Builder()
                                .userId("3132")
                                .username("fred")
                                .build()
                ).email(
                        new Email.Builder()
                                .address("test@maxmind.com")
                                .domain("maxmind.com")
                                .build()
                ).billing(
                        new Billing.Builder()
                                .firstName("First")
                                .lastName("Last")
                                .company("Company")
                                .address("101 Address Rd.")
                                .address2("Unit 5")
                                .city("City of Thorns")
                                .region("CT")
                                .country("US")
                                .postal("06510")
                                .phoneNumber("323-123-4321")
                                .phoneCountryCode("1")
                                .build()
                ).shipping(
                        new Shipping.Builder()
                                .firstName("ShipFirst")
                                .lastName("ShipLast")
                                .company("ShipCo")
                                .address("322 Ship Addr. Ln.")
                                .address2("St. 43")
                                .city("Nowhere")
                                .region("OK")
                                .country("US")
                                .postal("73003")
                                .phoneNumber("403-321-2323")
                                .phoneCountryCode("1")
                                .deliverySpeed(DeliverySpeed.SAME_DAY)
                                .build()
                ).payment(
                        new Payment.Builder()
                                .processor(Processor.STRIPE)
                                .wasAuthorized(false)
                                .declineCode("invalid number")
                                .build()
                ).creditCard(
                        new CreditCard.Builder()
                                .issuerIdNumber("323132")
                                .bankName("Bank of No Hope")
                                .bankPhoneCountryCode("1")
                                .bankPhoneNumber("800-342-1232")
                                .avsResult('Y')
                                .cvvResult('N')
                                .last4Digits("7643")
                                .build()
                ).order(
                        new Order.Builder()
                                .amount(323.21)
                                .currency("USD")
                                .discountCode("FIRST")
                                .affiliateId("af12")
                                .subaffiliateId("saf42")
                                .referrerUri(new URI("http://www.amazon.com/"))
                                .build()
                ).addShoppingCartItem(
                        new ShoppingCartItem.Builder()
                                .category("pets")
                                .itemId("ad23232")
                                .quantity(2)
                                .price(20.43)
                                .build()
                ).addShoppingCartItem(
                        new ShoppingCartItem.Builder()
                                .category("beauty")
                                .itemId("bst112")
                                .quantity(1)
                                .price(100.)
                                .build()
                ).device(
                        new Builder()
                                .ipAddress(InetAddress.getByName("81.2.69.160"))
                                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.89 Safari/537.36")
                                .acceptLanguage("en-US,en;q=0.8")
                                .build()
                ).build();
    }


    private String readJsonFile(String name) throws IOException, URISyntaxException {
        URL resource = MinFraudClientTest.class
                .getResource("/test-data/" + name + ".json");
        return new String(Files.readAllBytes(Paths.get(resource.toURI())), StandardCharsets.UTF_8);
    }

    public void verifyRequestFor(String service) throws IOException, URISyntaxException {
        String requestBody = this.readJsonFile("full-request");

        verify(postRequestedFor(urlMatching("/minfraud/v2.0/" + service))
                .withRequestBody(equalToJson(requestBody))
                .withHeader("Content-Type", matching("application/json")));
    }
}
