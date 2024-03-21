[![build](https://github.com/dobrosi/kotlin-feladat-ms/actions/workflows/ci.yml/badge.svg)](https://github.com/dobrosi/kotlin-feladat-ms/actions/workflows/ci.yml)
[![codecov](https://codecov.io/gh/dobrosi/kotlin-feladat-ms/graph/badge.svg?token=Bv0alq4qqQ)](https://codecov.io/gh/dobrosi/kotlin-feladat-ms)
# Kotlin oktatáshoz házifeladat

## Készíteni kell egy nagyon egyszerű alkalmazást / programot, az alábbi funkcionalitással
1. Open Meteo API-ról Budapestre 7 napos hőmérsékleti előrejelzés lekérése órás bontásban (https://api.open-meteo.com/v1/forecast?latitude=47.4984&longitude=19.0404&hourly=temperature_2m&timezone=auto)
2. Kapott eredményekre napi középhőmérséklet számítás
3. Eredmények konzolra kiírása
4. Egyszerű hibakezelés
5. Automata teszt(ek)

## Feladat megvalosítása során
1. Gradle build tool használata (Előkészítve) 
2. Tetszőleges Framework használata (Spring konfigurálva)
3. Data class-ok használata
4. Kotlin Collection Framework használata
5. Tetszőleges Http kliens
6. Tetszőleges deserialization library (Jackson konfigurálva)
7. JUnit teszt framework
8. Tetszőleges mock library (Mockk konfigurálva)
9. Mindenki saját git branch-en dolgozzon és onnan nyújtson be pull request-et a master-re.

## Szorgalmi feladat
- Nem csak konzolra írni az eredményt, hanem az alkalmazás (egyszerű webszerver) egy html oldalon elérhetővé teszi azt.
- Nem (csak) Spring Boot, hanem Kotlin-os framework-ok használata (Ktor, kotlinx.serialization, stb)

## Határidő
2024.03.20

## Gradle taskok

Alkalmazás indítása (Spring boot esetén): `gradlew bootRun`

Automata tesztek: `gradlew check`


