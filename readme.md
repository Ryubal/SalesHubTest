# Sales-Hub test

## Description
As per the instructions received, app contains 3 sections:
- Library:
	- User is able to add and remote movies
	- For the next (watched) movies, no instructions were given as to where to mark movies as watched, so I decided to do so in this section.
- Watched movies:
	- User will see a list of movies that were marked as watched in the library.
	- User is able to remove (mark as not watched) movies
- Watchlist
	- User will be able to add and remove movies
	- Movies added to the watchlist that also exist in the library will be marked
	- Adding or removing movies from the watchlist will not affect the library

## Technologies
Due to the short deadline given for this project, I decided to use Room for data persistance instead of a backend (thus Volley was not needed). Because of this, I also decided to use the benefits of Room (LiveData).
I should point out that this is the first time I've used LiveData (and ViewModels) in an Android app. I usually follow a View-Presenter-Repository design pattern with interfaces.

## Structure
I decided to separate all components, in case further modifications are needed for a single component.
- **MainActivity** - Entry point
- **common/new_movie** - Contains a reusable DialogFragment to add new movies
- **db** - Contains everything related to Room: DAOs, entities, repositories, and the DB initialization
- **library**- Contains the Library fragment, along its ViewModel and adapter
- **watched**- Contains the Watched movies fragment, along its ViewModel and adapter
- **watchlist**- Contains the Watchlist fragment, along its ViewModel and adapter