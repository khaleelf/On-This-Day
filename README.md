# On This Day - Historical Events

A demo application that showcases historical events that occurred on the current date throughout history.
Built for Lloyds Banking Group as a technical challenge.

## Features
- Displays historical events that occurred on the current date
- Groups events by year
- Clean and intuitive UI built with Jetpack Compose

## Technical Implementation / Requirements

### Architecture & Design Patterns
- **Modularization**:
    - Feature-based modularization [:feature:onthisday]
- **MVVM Pattern**: Implemented using ViewModel and StateFlow to manage UI state
- **Clean Architecture**: Feature module structured with clear separation of concerns:
    - Data layer: Api calls and data operations
    - Domain layer: Business logic and models
    - Presentation layer: ViewModels and UI components

### SOLID Principles
Classes / implementations have been kept specific and purpose driven. No mutable data / state has been
exposed by the classes. Interfaces and Sealed classes have had functionality extended.
Interfaces are concise to prevent bloat. And interfaces are used when injecting implementations.

### Technical Stack
- **Jetpack Compose**: Requested & Implemented.
- **Coroutines & Flow**: Requested & Implemented.
- **DI**: Requested, implemented with Hilt/Dagger.

### Testing
- Unit tests for ViewModel and data mapping
- Flow testing demonstrating proper handling of asynchronous operations
- Test coverage for core business logic

### App Screen Shot
![Screenshot_20241129_101530](https://github.com/user-attachments/assets/08e160e2-948b-4b31-af9a-09f4d96ff34d)


