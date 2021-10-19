
# spring boot unit test

use **@RunWith(SpringRunner.class) or @RunWith(MockitoJUnitRunner.class)**
when you are using Junit version < 5,

use **@ExtendWith(SpringExtension.class) or @ExtendWith(MockitoExtension.class)**
If you are using Junit version = 5,

use **@SpringBootTest** if you need to bootstracpe the whole spring boot, which is very slow.



