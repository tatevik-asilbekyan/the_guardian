# the_guardian

Requarments for App:

Create a news feed application with the following functionality:
·  The data should be pulled from http://open-platform.theguardian.com/documentation/search, using JSON format. The articles should include a title, an image and category.
·  The home page should be an infinite scrolling list of the articles (optional: ability to switch from list view to Pinterest-like view). Scrolling down the list should pull the next items from the feed.
·  Tapping an article should open it in a new page with shared element transition of the image.
·  Ability to save the article for offline use. The app should be able to open the offline items without network connection.
·  Ability to Pin items to the home page view using an icon in the article view page. The pinned items should be listed in a horizontal scrolling view on top of the home page list. When pinning an article, the new item should be reflected in the list dynamically, upon going back to the home page.
·  The app should check for new items in the feed and add them to the list every 30 seconds.
·  A background task to check for new items and generate a notification that new items are available. (The notification should be generated when the app is closed or taken to the background)


Application structure details:

· For Application Architecture used MVP.
· For Network used Retrofit and OkHttpClient(RxJava for observing data and Gson for mapping json to data).
· For image showing used Glide.
· For UI used material design, material components and constraint layout.
· For DB used Realm and SharedPreferences.
· For background task(new articles checking) used WorkManager(PeriodicWorkRequest) and ProcessLifecycleOwner(getting informed app starting and stopping).
· For listing items used RecyclerView with corresponding layout managers(StaggeredGrid and LinearLayout).
· For Item Detail used BottomSheetDialogFragment.
