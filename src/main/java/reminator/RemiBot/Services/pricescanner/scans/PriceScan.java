package reminator.RemiBot.Services.pricescanner.scans;

import net.dv8tion.jda.api.entities.TextChannel;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import reminator.RemiBot.Services.pricescanner.Product;
import reminator.RemiBot.Services.pricescanner.update.ProductPriceUpdate;
import reminator.RemiBot.utils.HTTPRequest;

import java.io.IOException;
import java.io.Serial;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PriceScan implements Scan {
    @Serial
    private static final long serialVersionUID = 2L;
    public static final Pattern NUMBER_PATTERN = Pattern.compile("[0-9]+( [0-9]+)?([,.][0-9]+)?");

    public Product product;
    public String mention;
    public String filter;

    static ChromeDriver driver;
    static {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--headless");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }

    public PriceScan(String mention, String name, String url, String filter) throws IOException {
        this.mention = mention;
        this.filter = filter;
        this.product = new Product(name, url, retrievePrice(url), true);
    }

    public float retrievePrice(String url) {
//        String rep = new HTTPRequest(url).GET();
        driver.get(url);
        String rep = driver.getPageSource();
        System.out.println(rep);
        String s = rep.split(filter+"[^0-9]*")[1];

        Matcher m = NUMBER_PATTERN.matcher(s);

        while(m.find()) {
            try {
                float price = Float.parseFloat(m.group(0).replaceAll(",", ".").replaceAll(" ", ""));
                if(price > 100) {
                    return price;
                }
            }catch (Exception ignored) { }
        }

        return -1;
    }

    @Override
    public String getUrl() {
        return product.url;
    }

    @Override
    public boolean scan(TextChannel channel) throws IOException {
        float newPrice = retrievePrice(product.url);
        ProductPriceUpdate update = product.updatePrice(newPrice);
        if(update.hasChange()) {
            channel.sendMessageEmbeds(update.toEmbed()).queue();
            channel.sendMessage(mention).queue();
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return product + " - " + product.url + " - Prix : " + product.price + "€";
    }
}
