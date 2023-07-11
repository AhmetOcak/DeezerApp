# Deezer App

<img src="https://github.com/AhmetOcak/AppcentDeezerApp/assets/73544434/67ff2e4b-c548-4d98-8eb1-cbd76cb9cae0" width="192" height="192"/>

Uygulama Kotlin dili kullanılarak yazılmıştır. Uygulamanın yapımda Jetpack Compose kullanılmıştır. Uygulamada açılan ilk ekranda müzik türleri listelenmiştir. Aynı sayfada <b>Beğenilenler</b> sayfasına giden bir kalp iconu bulunmaktadır. Müzik türlerinden biri seçildiği zaman ilgili müzik türü ile alakalı sanatçıların listelendiği <b>Sanatçılar</b> sayfasına gidilir. Listelenen sanatçılardan biri seçildiği zaman ilgili sanatçının detaylarının gösterildiği ve albümlerinin listelendiği <b>Sanatçı Detay</b> sayfasına gidilir. Listelenen albümlerden biri seçildiği zaman ilgili albüme ait şarkıların listelendiği <b> Albüm Detay </b> sayfasına gidilir. Bu sayfada istenilen şarkı favorilere eklenebilir, favorilerde ekle bir şarkı var ise ilgili şarkı favorilerden kaldırabilir. Seçilen şarkıların 30 saniyelik önizlemesi çalar. Önizleme istenildiği zaman durdurulabilir. Favoriler sayfasında da eklenen şarkıların 30 saniyelik önizlemesi çalınabilir ve istenildiği zaman durdurulabilir. İstenilen şarkı favorilerden kaldırılabilir. Uygulama açık ve kapalı temalarla uyumlu bir şekilde çalışmaktadır.

## Tech Stack 📚

* [Navigation](https://developer.android.com/jetpack/compose/navigation)

* [ViewModel](https://developer.android.com/jetpack/compose/libraries#viewmodel)

* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)

* [Accompanist](https://google.github.io/accompanist/systemuicontroller/)

* [Animations](https://developer.android.com/jetpack/compose/animation)

* [Retrofit](https://square.github.io/retrofit)

* [Coil](https://coil-kt.github.io/coil)

* [Okhttp](https://square.github.io/okhttp/)

## Video from app 📱

<div>
  
  <video src='https://github.com/AhmetOcak/AppcentDeezerApp/assets/73544434/f36bc2b5-cd8a-4d36-a056-117755c9ebe1' />
  
</div>

## Outputs 🖼

|                    | Dark | Light |
|--------------------|------|-------|
| Müzik Kategorileri | <img src="https://github.com/AhmetOcak/AppcentDeezerApp/assets/73544434/cddde99e-8ea9-4867-b40d-71e709385862" width="240" height="480"/>     | <img src="https://github.com/AhmetOcak/AppcentDeezerApp/assets/73544434/b71db5ee-51e1-40c8-a5f9-ce0ba536192b" width="240" height="480"/>      |
| Sanatçı Listeleme  | <img src="https://github.com/AhmetOcak/AppcentDeezerApp/assets/73544434/7431094e-7c76-478f-9683-c54e85791bcb" width="240" height="480"/>     |  <img src="https://github.com/AhmetOcak/AppcentDeezerApp/assets/73544434/42e08cd0-dcc5-4c4a-a9ca-f4fcfa558ec7" width="240" height="480"/>     |
| Sanatçı Detay      | <img src="https://github.com/AhmetOcak/AppcentDeezerApp/assets/73544434/0957e4e5-e1ff-4313-978b-5c7aaf3c2a0c" width="240" height="480"/>     |  <img src="https://github.com/AhmetOcak/AppcentDeezerApp/assets/73544434/5e9feace-77fa-4232-8ba9-c6ac4a64d82d" width="240" height="480"/>     |
| Albüm Detay        | <img src="https://github.com/AhmetOcak/AppcentDeezerApp/assets/73544434/4b0425b8-b642-4562-b554-509eea88f8d2" width="240" height="480"/>     |  <img src="https://github.com/AhmetOcak/AppcentDeezerApp/assets/73544434/4c337053-0b15-4281-b4a6-225ca1337dc5" width="240" height="480"/>     |
| Beğenilenler       | <img src="https://github.com/AhmetOcak/AppcentDeezerApp/assets/73544434/e009941b-fbd4-4153-96f5-23fd49d727f3" width="240" height="480"/>     |  <img src="https://github.com/AhmetOcak/AppcentDeezerApp/assets/73544434/f801affa-a08a-47d7-bccb-0802865fbdad" width="240" height="480"/>     |


## Modularization 📦

<img src="https://github.com/AhmetOcak/AppcentDeezerApp/assets/73544434/56ca258d-6615-495d-9744-99a4a32c3683"/>
 
* ``:app`` Navigasyondan sorumlu modül.
* ``:feature:albumdetail``,``:feature:artistdetail`` ,``:feature:artists`` ,``:feature:favorites`` ,``:feature:musicgenres`` Her modül bir ekranı temsil ediyor.
* ``:feature:designsystem`` Uygulamada bulunan componentleri ve iconları barındırır. Uygulamanın temasını ayarlar.
* ``:feature:ui`` Ekranların ortak olarak kullandıkları componentleri barındırır.
* ``:models`` Ui tarafında kullanılacak modelleri barındırır.
* ``:domain:usecases`` Usecaseleri barındırır. Data katmanındaki repository ler ile ui arasında köprü görevi görür.
* ``:data:albumdetail``,``:data:artistdetail`` ,``:data:artists`` ,``:data:favoritesongs`` ,``:data:musicgenres`` Kaynaklardan verileri alır ve ``:domain:usecase`` modülü aracılığıyla Ui tarafına gönderir.
 
* ``:data:network`` API lere istek gönderir ve dönen yanıtları işler.
* ``:data:database`` Room kullanarak verileri local bir database de depolar.

## Architecture 🏗
Uygulamada MVVM [Model-View-ViewModel] mimarisi kullanılmıştır

![mvvm](https://user-images.githubusercontent.com/73544434/197416569-d42a6bbe-126e-4776-9c8f-2791925f738c.png)
