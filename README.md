🚀 Giphy Searcher: Technical Project Overview
This project delivers a highly stable GIF search application built on a modern Model-View-Intent (MVI) architecture using Jetpack Compose and Kotlin Flows.

🌟 Quality and Architectural Summary
The implementation strictly adheres to the Unidirectional Data Flow (UDF) pattern, ensuring a predictable, scalable, and fully maintainable codebase.

Core Implementation Features
Architecture: MVI is utilized for state management, with the View observing a single immutable StateFlow. This structure supports the Single-Activity Architecture via Compose Navigation.

Asynchronous Logic: Auto Search is implemented efficiently using Kotlin Flow's .debounce() operator, minimizing API calls and managing cancellation via .flatMapLatest().

Decoupled Data Pipeline: A dedicated Mapper Layer is implemented to convert DTOs (Gson) into clean Domain Models (GifModel). This guarantees the UI is stable and isolated from network structure changes.

Paging: Jetpack Paging 3 is used to stream search and trending results, providing memory-efficient and seamless loading upon scrolling.

Network Resilience (Bonus): Real-time Network Availability Handling is integrated via a Flow-based observer (ConnectivityManager), allowing the UI to instantly display an offline warning and condition network requests on connectivity.

GIF Animation: Animation decoding is correctly configured via the Hilt-provided singleton ImageLoader using ImageDecoderDecoder.Factory(), ensuring GIFs play automatically across all supported devices.

📁 Project Structure
The codebase is organized into distinct layers for clarity and scalability:

chili.labs.giphysearcher/
├── domain/              # Clean Business Models (GifModel, ImageUrls)
├── data/mapper/         # Conversion logic (GifMapper)
├── network/             # Retrofit, Gson setup, ConnectivityObserver
├── pagination/          # GiphyPagingSource logic
├── screens/             # MVI ViewModels and Composables
└── ui/                  # Reusable components and layout logic
