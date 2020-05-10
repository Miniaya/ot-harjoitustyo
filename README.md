# Battleships

Sovellus on oma versioni klassisesta laivanupotus-pelistä, jossa tarkoituksena on "upottaa" vastustajan laivat pelilaudalta ennen kuin vastustaja saa upotettua sinun laivasi.

## Dokumentaatio

[Käyttöohje](https://github.com/Miniaya/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)

[Vaatimusmäärittely](https://github.com/Miniaya/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/Miniaya/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Testausdokumentti](https://github.com/Miniaya/ot-harjoitustyo/blob/master/dokumentaatio/testaus.md)

[Työaikakirjanpito](https://github.com/Miniaya/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)

## Releaset

[Viikko 5](https://github.com/Miniaya/ot-harjoitustyo/releases/tag/viikko5)

[loppupalautus](https://github.com/Miniaya/ot-harjoitustyo/releases/tag/loppupalautus)

## Komentorivitoiminnot

### Pelin käynnistäminen

Ohjelman käynnistäminen onnistuu NetBeansin kautta tai komentorivillä komennolla 

```
mvn compile exec:java -Dexec.mainClass=battleships.ui.Main
```

### Suoritettavan jarin generointi

```
mvn package
``` 

generoi kansioon target jar-tiedoston _Battleships-1.0-SNAPSHOT.jar_

### Testaus

Testit suoritetaan ja testikattavuusraportti luodaan komennolla 

```
mvn test jacoco:report
```

raporttia voi tarkastella avaamalla selaimessa tiedosto _target/site/jacoco/index.html_

### Checkstyle

Tiedostoon checkstyle.xml määritellyt tarkistukset suoritetaan komennolla 

```
mvn jxr:jxr checkstyle:checkstyle
```

Mahdollisia virheitä voi tarkastella avaamalla selaimessa tiedosto _target/site/checkstyle.html_

### JavaDoc

JavaDoc generoidaan komennolla 

```
mvn javadoc:javadoc
```

JavaDocia voi tarkastella avaamalla selaimessa tiedosto _target/site/apidocs/index.html_
