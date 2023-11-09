# About

**CoinPlot** is a simple and modern web app for exchange rate history visualization and basic analytics.
Its core features include:

* Support for the following exchange rate data providers:
    * [Fixer](https://fixer.io/)
    * [Frankfurter](https://www.frankfurter.app/) *(including self-hosted instances)*
* An efficient internal architecture that makes heavy use of caching,
  which helps substantially minimize or completely avoid outgoing API requests
* Localization in multiple languages
* Modern, slick and responsive UI

# Building & Installing

*Coming soon!*

# Contributing

## Localization

*Coming soon!*

## Data Providers

1. Create a separate package for all classes related to the new data provider and place it in `backend`,
   such as:`io.github.northmaxdev.coinplot.backend.fixer`, `io.github.northmaxdev.coinplot.backend.frankfurter`.
   **It is recommended for all classes to be package-private,**
   as the rest of the codebase interacts only with the interfaces of `io.github.northmaxdev.coinplot.backend.core`.
2. Implement the [`CurrencyService`](src/main/java/io/github/northmaxdev/coinplot/backend/core/currency/CurrencyService.java)
   interface. If your data provider is a web API, you should consider extending
   [`AbstractCurrencyFetchService`](src/main/java/io/github/northmaxdev/coinplot/backend/core/currency/AbstractCurrencyFetchService.java),
   in which case you'll need to implement a
   [`CurrencySetRequest`](src/main/java/io/github/northmaxdev/coinplot/backend/core/currency/CurrencySetRequest.java), a DTO and a
   [`CurrencySetDTOMapper`](src/main/java/io/github/northmaxdev/coinplot/backend/core/currency/CurrencySetDTOMapper.java).
3. Implement the [`ExchangeRateService`](src/main/java/io/github/northmaxdev/coinplot/backend/core/exchange/ExchangeRateService.java)
   interface. If your data provider is a web API, you should consider extending
   [`AbstractExchangeRateFetchService`](src/main/java/io/github/northmaxdev/coinplot/backend/core/exchange/AbstractExchangeRateFetchService.java),
   in which case you'll need to implement
   an [`ExchangeRateSetRequest`](src/main/java/io/github/northmaxdev/coinplot/backend/core/exchange/ExchangeRateSetRequest.java), a DTO and
   an [`ExchangeRateSetDTOMapper`](src/main/java/io/github/northmaxdev/coinplot/backend/core/exchange/ExchangeRateSetDTOMapper.java).
   You may extend
   [`AbstractExchangeRateSetDTOMapper`](src/main/java/io/github/northmaxdev/coinplot/backend/core/exchange/AbstractExchangeRateSetDTOMapper.java)
   for convenience or simply use
   [`CommonExchangeRateSetDTOMapper`](src/main/java/io/github/northmaxdev/coinplot/backend/core/exchange/CommonExchangeRateSetDTOMapper.java)
   if it's compatible with your web API.
4. Implement the [`DataProvider`](src/main/java/io/github/northmaxdev/coinplot/backend/core/DataProvider.java) interface.
   There is no need to register it anywhere as
   [`DataProviderService`](src/main/java/io/github/northmaxdev/coinplot/backend/core/DataProviderService.java)
   will automatically pick up on it through Spring DI.
   The `ID` property is used for selecting the desired data provider when deploying the app.
   [`AbstractDataProvider`](src/main/java/io/github/northmaxdev/coinplot/backend/core/AbstractDataProvider.java)
   may be extended for convenience.

Some extra notes:

* You will generally want a base class for your
  [`CurrencySetRequest`](src/main/java/io/github/northmaxdev/coinplot/backend/core/currency/CurrencySetRequest.java) and
  [`ExchangeRateSetRequest`](src/main/java/io/github/northmaxdev/coinplot/backend/core/exchange/ExchangeRateSetRequest.java).
  It is recommended for this base class to itself extend
  [`AbstractAPIRequest`](src/main/java/io/github/northmaxdev/coinplot/backend/core/web/request/AbstractAPIRequest.java).
* Either way, it is recommended to explore the codebase and see how existing data providers are implemented to get the general idea.

# Licensing

See the [license file](LICENSE) for more information.
