<h1 align="center">TheMovieDB App</h1>
<p align="center">
  TheMovieDB App is an application written with a focus on the MVVM architecture, Jetpack Components, Clean Architecture, and adhering to SOLID principles. It incorporates features such as offline caching, video playback using ExoPlayer, infinite scrolling, and more.
</p>

<p align="center">
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/Android API-24%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://kotlinlang.org"><img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-1.8.21-blue"/></a>
  <img alt="MVVM" src="https://img.shields.io/badge/MVVM-Architecture-purple"/>
</p>

## Getting Started

### Installation
2. Enter your API in `build.gradle`
   ```js
   buildConfigField("String","API_TOKEN","You have to enter the token you got from TheMovieDB")



## Screenshots
<table>
  <tr>
    <td><img src="https://github.com/murat-aydin/TheMovieDb/assets/108257930/e32c915b-9221-412a-a166-83ce4873dc99" width="100%"></td>
    <td><img src="https://github.com/murat-aydin/TheMovieDb/assets/108257930/96f43571-d180-4bf4-b58f-d38ed8c9dc53" width="100%"></td>
  </tr>  
  <tr>
    <td><img src="https://github.com/murat-aydin/TheMovieDb/assets/108257930/a1ff86d9-f09b-4ebc-a237-386f3f339c54" width="100%"></td>
    <td><img src="https://github.com/murat-aydin/TheMovieDb/assets/108257930/9306c303-fcf5-49d5-baea-37555e55bb88" width="100%"></td>




  </tr>  

</table>


<h3>Tech stack & Open-source libraries</h3>

<ul>
  <li><strong>100% Kotlin</strong> based + <strong>Coroutines</strong> and <strong>Flow</strong></li>
  <li>An example of using <strong>Build Variants</strong>, <strong>Product Flavors</strong>, <strong>Kotlin DSL</strong> are given.</li>
  <li><strong>MVVM Architecture</strong> - Modern, maintainable, and Google suggested app architecture</li>
  <li><strong>Navigation Component</strong> - Single activity multiple fragments approach</li>
  <li><strong>Flow</strong> - Notify domain layer data to views.</li>
  <li><strong>Lifecycle</strong> - Dispose of observing data when lifecycle state changes.</li>
  <li><strong>View Binding</strong> - Allows you to more easily write code that interacts with views</li>
  <li><strong>ViewModel</strong> - UI related data holder, lifecycle aware.</li>
  <li><strong>Hilt</strong> - Dependency injection library for Android</li>
  <li><strong>Retrofit</strong> - It is used for http requests in the app.</li>
  <li><strong>Room DB</strong> - The Room persistence library provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite</li>
  <li><strong>ExoPlayer</strong> - It is used to play the videos contained in the app.</li>
  <li><strong>Coil</strong> - It is used to upload images contained in the app.</li>
  <li><strong>Chucker</strong> - It is used to follow the requests made in the app in detail.</li>
</ul>

<h3>Layers :bookmark_tabs:</h3>

<ul>
  <li><strong>Domain</strong> - Executes business logic independent of any layer, a pure Kotlin/Java package with no Android-specific dependencies.</li>
  <li><strong>Data</strong> - Dispenses required data for the application to the domain layer by implementing interfaces exposed by the domain.</li>
  <li><strong>Presentation</strong> - Includes both domain and data layers, Android-specific, and executes UI logic.</li>
</ul>