package com.example.gardencoffeeproduct.repository;

import com.example.gardencoffeeproduct.model.Category;
import com.example.gardencoffeeproduct.model.Product;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductJdbcRepositoryTest {
    static EmbeddedMysql embeddedMysql;


    @BeforeAll
    static void setup(){
        var config = aMysqldConfig(Version.v8_0_11)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test","test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(config)
                .addSchema("test-order_mgmt", ScriptResolver.classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    static void cleanup(){
        embeddedMysql.stop();
    }

    @Autowired
    ProductRepository repository;

    private static final Product newProduct = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L);
    @Test
    @Order(1)
    @DisplayName("상품을 추가할 수 있다.")
    void testInsert(){
        repository.insert(newProduct);
        var all = repository.findAll();
        assertThat(all.isEmpty(), is(false));
    }

    @Test
    @Order(2)
    @DisplayName("상품을 이름으로 조회할 수 있다.")
    void testFindByName(){
        var product = repository.findByName(newProduct.getProductName());
        assertThat(product.isEmpty(), is(false));
    }

    @Test
    @Order(3)
    @DisplayName("상품을 ID로 조회할 수 있다.")
    void testFindById(){
        var product = repository.findById(newProduct.getProductId());
        assertThat(product.isEmpty(), is(false));
    }
    @Test
    @Order(4)
    @DisplayName("상품을 카테고리로 조회할 수 있다.")
    void testFindByCategory(){
        var product = repository.findByCategory(Category.COFFEE_BEAN_PACKAGE);
        assertThat(product.isEmpty(), is(false));
    }
    @Test
    @Order(5)
    @DisplayName("상품을 삭제할 수 있다.")
    void testUpdateById(){
        newProduct.setProductName("updated-name");
        repository.update(newProduct);
        var product = repository.findById(newProduct.getProductId());
        assertThat(product.isEmpty(),is(false));
        assertThat(product.get(),samePropertyValuesAs(newProduct));
    }
    @Test
    @Order(6)
    @DisplayName("상품을 삭제할 수 있다.")
    void testDeleteAll(){
        repository.deleteAll();
        var findAll = repository.findAll();
        assertThat(findAll.isEmpty(), is(true));
    }
}