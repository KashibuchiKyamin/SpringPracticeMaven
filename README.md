# SpringPracticeMaven
Spring徹底入門 #14 チュートリアルを参照して作業

## バージョン
- AdoptOpenJDK 11
- Spring Boot 2
    - Spring Data JPA 2
    - Thymeleaf 3
- Lombok 1.18.12
- PostgreSQL 12
    
## 変更点・メモ
### プロパティファイルの設定
- 以下を追加
```
spring.datasource.initialization-mode=always
spring.jpa.open-in-view=true
```
### Java8Dialect
- Java8DialectのBean定義は不要

### 各Entity
- Lombokを使用しているため、各EntityにGetter/Setterは記述していない
- hibernate-core 5.4.12のため、AttributeConvertorは作成せず、  
  ```@Column(name = "カラム名", columnDefinition = "DATE")```を付与

### SQL文
- ```ALTER TABLE```でForeign keyを指定するのが面倒臭かったため、書籍と書き方を変更

### DI方法について
- コンストラクタインジェクションで記述する
```
public class Aaaa {
    @Autowired
    Bbbb bbbb;
}
```
ではなく
```
public class Aaaa {
    private Bbbb bbbb;

    @Autowired
    public void setBbbb(Bbbb bbbb) {
        this.bbbb = bbbb;
    }
}
```
Lombokの```@AllArgsConstructor```も併用する
