## demo CRUD on R2DBC 

* findBy
* create 
* update
* delete

## spring boot unit test

use **@RunWith(SpringRunner.class) or @RunWith(MockitoJUnitRunner.class)**
when you are using Junit version < 5,

use **@ExtendWith(SpringExtension.class) or @ExtendWith(MockitoExtension.class)**
If you are using Junit version = 5,

use **@SpringBootTest** if you need to bootstracpe the whole spring boot, which is very slow.

# manually validate bean
```java
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    CatalogueItem c3 = new CatalogueItem(1L, "ww", "abc", "aaa@163.com",
        "cat", 10.0, 5, Instant.ofEpochSecond(10L), null);
    Set<ConstraintViolation<Object>> err = validator.validate(c3);
```