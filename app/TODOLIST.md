# CineFlix TODO List

## Features

### [x] Home Screen - Popular Movies

- [x] Display list of popular movies
- [x] Show movie title
- [x] Show backdrop image
- [x] Show release date
- [x] Show vote average (star rating)
- [x] Implement favorite button functionality
- [x] Store favorite status locally
- [x] Implement refresh gesture
- [x] Fix Rating Color
- 
[//]: # (- [x] Add Favorite Button)
[//]: # (- [ ] Add Favorite Movie List Fragment)
- [x] Add Top Textview with App Name

### UI Theme

- [x] Find color palette for the app
- [ ] Create Light Theme
- [ ] Change app icon
 
### Offline Mode

- [x] Implement local storage for favorite movies
- [x] Create offline data persistence system
- [x] Add offline mode detection
- [x] Show only favorite movies when offline
- [x] Implement offline data sync mechanism
- [x] Wifi Monitoring

### Fragment Navigation

- [x] Implement Fragment navigation from Fragment to Fragment (without duplicate stack entries)

### Movie Details Screen

- [x] Create Movie Details Activity/Fragment
- [x] Implement click listener on movie items
- [x] Display detailed movie information:
    - [x] Movie image
    - [x] Title
    - [x] Genre(s)
    - [x] Release date
    - [x] Star rating
    - [x] Runtime
    - [x] Favorite status
    - [x] Description
    - [x] Cast list (located at Movie-Credits)
    - [x] Reviews (up to 3)
    - [x] Similar movies section (up to 6) (Optional)
- [x] Implement share button for movie homepage URL
    - [x] Hide share button when URL doesn't exist
- [x] Add back button navigation

### API Integration for Details Screen

- [x] Implement movie details API call
- [x] Implement movie credits API call
- [x] Implement movie reviews API call
- [x] Implement similar movies API call

### Skeleton Loader (Optional)

- [x] Add pull-to-refresh functionality (Optional)
- [ ] Implement skeleton loader (Optional)
    - [ ] Create skeleton layout for movie items
    - [ ] Add loading animation
    - [ ] Handle transition between skeleton and actual content

### Testing & Polish

- [ ] Add error handling for API failures
- [x] Implement loading states
- [ ] Add empty state handlers
- [ ] Test offline functionality
- [ ] Test edge cases
- [ ] Perform UI/UX testing
- [ ] Test on different screen sizes
- [ ] Test on different Android versions

## Technical Requirements to Verify

- [x] Ensure proper scrolling implementation
- [x] Verify all API integrations
- [ ] Check local storage implementation
- [ ] Verify share functionality using native sharing
- [ ] Test network state handling
- [ ] Verify favorite status persistence after app kill

## Notes

- TMDB API endpoints to use:
    - Popular movies: `/movie/popular`
    - Movie details: `/movie/{movie_id}?append_to_response=credits`
    - Movie reviews: `/movie/{movie_id}/reviews`
    - Similar movies: `/movie/{movie_id}/similar`
- Don't use inclusion parameters for reviews and similar movies
- Implement proper offline mode for favorite movies
- Focus on clean architecture and code organization
