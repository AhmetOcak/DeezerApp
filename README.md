# Deezer App

<img src="https://github.com/AhmetOcak/AppcentDeezerApp/assets/73544434/67ff2e4b-c548-4d98-8eb1-cbd76cb9cae0" width="192" height="192"/>

<!--
Uygulama Kotlin dili kullanılarak yazılmıştır. Uygulamanın yapımda Jetpack Compose kullanılmıştır. Uygulamada açılan ilk ekranda müzik türleri listelenmiştir. Aynı sayfada <b>Beğenilenler</b> sayfasına giden bir kalp iconu bulunmaktadır. Müzik türlerinden biri seçildiği zaman ilgili müzik türü ile alakalı sanatçıların listelendiği <b>Sanatçılar</b> sayfasına gidilir. Listelenen sanatçılardan biri seçildiği zaman ilgili sanatçının detaylarının gösterildiği ve albümlerinin listelendiği <b>Sanatçı Detay</b> sayfasına gidilir. Listelenen albümlerden biri seçildiği zaman ilgili albüme ait şarkıların listelendiği <b> Albüm Detay </b> sayfasına gidilir. Bu sayfada istenilen şarkı favorilere eklenebilir, favorilerde ekle bir şarkı var ise ilgili şarkı favorilerden kaldırabilir. Seçilen şarkıların 30 saniyelik önizlemesi çalar. Önizleme istenildiği zaman durdurulabilir. Favoriler sayfasında da eklenen şarkıların 30 saniyelik önizlemesi çalınabilir ve istenildiği zaman durdurulabilir. İstenilen şarkı favorilerden kaldırılabilir. Uygulama açık ve kapalı temalarla uyumlu bir şekilde çalışmaktadır.
-->

The application is developed using the Kotlin language and incorporates Jetpack Compose. On the initial screen of the app, music genres are listed. On the same page, there is a heart icon that leads to the 'Favorites' page. When a music genre is selected, it takes you to the 'Artists' page, which lists artists related to the chosen music genre. When you select a specific artist, it displays details about the artist and lists their albums on the 'Artist Details' page. If you select an album from the list, it takes you to the 'Album Details' page, where you can add songs to your favorites. You can also remove songs from your favorites if they are already added. A 30-second preview of the selected songs is available, and you can stop the preview whenever you like. In the 'Favorites' page, you can play 30-second previews of the added songs and stop them as needed. You can also remove songs from your favorites. The application is compatible with both light and dark themes.

## Tech Stack 📚

* [Navigation](https://developer.android.com/jetpack/compose/navigation)

* [ViewModel](https://developer.android.com/jetpack/compose/libraries#viewmodel)

* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)

* [Accompanist](https://google.github.io/accompanist/systemuicontroller/)

* [Animations](https://developer.android.com/jetpack/compose/animation)

* [Retrofit](https://square.github.io/retrofit)

* [Coil](https://coil-kt.github.io/coil)

* [Okhttp](https://square.github.io/okhttp/)

* [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview?hl=en)

## Video from app 📱

<div>
  
  <video src='https://github.com/AhmetOcak/DeezerApp/assets/73544434/959b542c-ef5a-4bdc-b94b-69c4f6a576f3' />
  
</div>

## Outputs 🖼

|                    | Dark | Light |
|--------------------|------|-------|
| Music Genres | <img src="https://github.com/AhmetOcak/AppcentDeezerApp/assets/73544434/cddde99e-8ea9-4867-b40d-71e709385862" width="240" height="480"/>     | <img src="https://github.com/AhmetOcak/AppcentDeezerApp/assets/73544434/b71db5ee-51e1-40c8-a5f9-ce0ba536192b" width="240" height="480"/>      |
| Artists  | <img src="https://github.com/AhmetOcak/AppcentDeezerApp/assets/73544434/7431094e-7c76-478f-9683-c54e85791bcb" width="240" height="480"/>     |  <img src="https://github.com/AhmetOcak/AppcentDeezerApp/assets/73544434/42e08cd0-dcc5-4c4a-a9ca-f4fcfa558ec7" width="240" height="480"/>     |
| Artist Detail      | <img src="https://github.com/AhmetOcak/DeezerApp/assets/73544434/ccaae8d5-f531-46b0-8a6a-bf7d33451d0c" width="240" height="480"/>     |  <img src="https://github.com/AhmetOcak/DeezerApp/assets/73544434/e1e6416b-c030-42ab-8d03-4fb14abc7590" width="240" height="480"/>     |
| Album Detail        | <img src="https://github.com/AhmetOcak/DeezerApp/assets/73544434/f6508ea8-4206-4207-93af-ab648dc426c8" width="240" height="480"/>     |  <img src="https://github.com/AhmetOcak/DeezerApp/assets/73544434/d4df63dd-03ef-421d-a75d-a92acbbfaff4" width="240" height="480"/>     |
| Favorites       | <img src="https://github.com/AhmetOcak/AppcentDeezerApp/assets/73544434/e009941b-fbd4-4153-96f5-23fd49d727f3" width="240" height="480"/>     |  <img src="https://github.com/AhmetOcak/AppcentDeezerApp/assets/73544434/f801affa-a08a-47d7-bccb-0802865fbdad" width="240" height="480"/>     |
| Play Music       | <img src="https://github.com/AhmetOcak/DeezerApp/assets/73544434/89f05073-21ca-484f-86a3-cc9ce32faef0" width="240" height="480"/>     |  <img src="https://github.com/AhmetOcak/DeezerApp/assets/73544434/5e42a0ab-b128-469b-bf60-a3b4c47f0739" width="240" height="480"/>     |


## Modularization 📦

<img src="https://github.com/AhmetOcak/AppcentDeezerApp/assets/73544434/56ca258d-6615-495d-9744-99a4a32c3683"/>
 
* ``:app`` The module responsible for navigation.
* ``:feature:albumdetail``,``:feature:artistdetail`` ,``:feature:artists`` ,``:feature:favorites`` ,``:feature:musicgenres``, ``:feature:playmusic`` Each module represents a screen.
* ``:feature:designsystem``  It houses the components and icons present in the application. It sets the theme of the application.
* ``:feature:ui`` It houses the common components used by screens.
* ``:models`` It contains the models to be used on the UI side.
* ``:domain:usecases``  It houses use cases. It serves as a bridge between the data layer's repositories and the UI.
* ``:data:albumdetail``,``:data:artistdetail`` ,``:data:artists`` ,``:data:favoritesongs`` ,``:data:musicgenres`` It retrieves data from sources and sends it to the UI through the ``:domain:usecase`` module. 
 
* ``:data:network``  It sends requests to APIs and processes the returned responses.
* ``:data:database`` It stores data in a local database using Room.

## Architecture 🏗
The app uses MVVM [Model-View-ViewModel] architecture to have a unidirectional flow of data, separation of concern, testability, and a lot more.

![mvvm](https://user-images.githubusercontent.com/73544434/197416569-d42a6bbe-126e-4776-9c8f-2791925f738c.png)
