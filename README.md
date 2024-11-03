## Tech Stack
- Jetpack Compose
- Clean Architecture
- Multi-Module
- MVVM
- Dagger Hilt
- Coroutine
- Custom Plugin(Kotlin-DSL)
- Version Catalog
- Flow
- Data Store
- Navigation
- Coil Compose
- Retrofit2, okHttp3

## Architecture
```
├── app
|   ├── app
|   └── di
│
├── build-logic
|   └── plugin
│
├── data
|   ├── service
|   ├── base
|   ├── model
|   ├── datastore
|   ├── mapper
|   └── repository
│
├── core
│   ├── ui
│   │   ├── base
│   │   ├── common
│   │   └── util
│   ├── main
│   │   ├── base
│   │   ├── enums
│   │   └── util
│   └── navigation
│       └── navigation
│
├── domain
│   ├── base
│   ├── model
│   ├── repository
│   └── usecase
│
├── design-system
│   └── design-system
│
└── feature
    ├── backlog
    ├── history
    ├── login
    ├── mypage
    ├── setting
    ├── splash
    ├── today
    └── yesterdaylist
```
