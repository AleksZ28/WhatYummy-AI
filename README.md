# WhatYummy AI

![Kotlin](https://img.shields.io/badge/kotlin-v2.3.21-blue.svg?logo=kotlin)
![Compose](https://img.shields.io/badge/Jetpack_Compose-v2026.05.01-green.svg?logo=jetpackcompose)
![Firebase](https://img.shields.io/badge/Firebase-Firestore_%26_Auth-orange.svg?logo=firebase)

**WhatYummy AI** is a culinary application that simplifies recipe organization and everyday cooking. It allows you to search for recipes, add your own, and use a simple voice assistant while preparing meals. The application was also designed with people with gluten intolerance in mind.

---

## Screenshots



---

## App Features

### 🔍 Browsing and Searching
- **Search by ingredients**: Check what you can cook with the products you currently have in your kitchen.
- **Search Engine**: Find recipes by name (supports typos and fuzzy matching).
- **Filters**: Quickly toggle to **gluten-free** dishes and browse by **categories** (e.g., dinners, desserts).

### 📖 Recipe Management
- **Personal Recipe Book**: Add your own dishes with photos, ingredient lists, and preparation descriptions. Recipes can be public or private.
- **Data Editing**: Ability to update recipe content at any time.
- **Your Kitchen**: A list of products you own – the app filters available recipes based on it.

### 🗣️ Cooking Assistance
- **Hands-Free Cooking**: Switch recipe steps using voice commands (*"next"*, *"back"*, *"repeat"*) without touching the screen. Instructions are read aloud.
- **Portion Calculator**: Choose the number of portions, and the app will automatically recalculate the ingredient amounts.
- **Ingredient Hints**: For each recipe, you can see which products you have in your kitchen and which ones you are missing.

### 🔐 Security and Sync
- **Google Login**: Quick and secure access to the app using your Google account.
- **Cloud Synchronization**: Your recipes and kitchen contents are securely stored in Firebase, so you can access them on any device.

---

## Coming Soon:

- **AI Integration**: Intelligent recipe search.
- **App functionality improvements**

---

## Tech Stack

- **Language**: Kotlin (2.3.21)
- **UI**: Jetpack Compose (Material 3)
- **Backend**: Firebase (Firestore, Authentication, Analytics)
- **Multimedia**: Cloudinary (Image management), Coil (Image loading)
- **Navigation**: Compose Navigation (2.9.8)
- Speech-to-Text (STT) & Text-to-Speech (TTS) for the cooking assistant
- FuzzyWuzzy Kotlin (Ingredient matching logic)

---

## Installation and Setup

1. **Clone the repository**:
   ```
   git clone https://github.com/AleksZ28/WhatYummy-AI.git
   ```

2. **Firebase Configuration**:
   - Add the `google-services.json` file from Firebase to the `app/` folder.
   - Enable Firestore Database and Authentication (Google Provider) in the Firebase console.

3. **Cloudinary Configuration**:
   - Configure `cloud_name` in the `WhatYummyApp.kt` class.
   - Make sure you have the correct `upload_preset` set in `AddRecipeViewModel`.

4. **Build and Run**:
   - Open the project in Android Studio.
   - Sync Gradle and run on an emulator or a physical device with at least SDK 26.

> **Important (App Signing)**: The project is configured for a specific `debug.keystore` file. If you don't have it, generate it in the `app/signing/` folder using the command below (password: `android`):
> ```bash
> keytool -genkey -v -keystore debug.keystore -storepass android -alias androiddebugkey -keypass android -keyalg RSA -keysize 2048 -validity 10000 -dname "CN=Android Debug,O=Android,C=US"
> ```
> Remember to add the SHA-1 certificate of the generated key to your Firebase console, otherwise Google Login will not work.

---



# 🇵🇱 WhatYummy AI

**WhatYummy AI** to aplikacja kulinarna, która ułatwia organizację przepisów i codzienne gotowanie. Pozwala na wyszukiwanie przepisów, dodawanie własnych oraz korzystanie z prostego asystenta głosowego podczas przygotowywania posiłków. Aplikacja została stworzona również z myślą o osobach z nietolerancją glutenu.

---

## Zrzuty ekranu



---

## Funkcje aplikacji

### 🔍 Przeglądanie i wyszukiwanie
- **Szukanie po składnikach**: Sprawdź, co możesz ugotować z produktów, które masz aktualnie w kuchni.
- **Wyszukiwarka**: Znajduj przepisy po nazwie (obsługuje literówki i niedokładne nazwy).
- **Filtry**: Szybkie przełączanie na dania **bezglutenowe** oraz przeglądanie według **kategorii** (np. obiady, desery).

### 📖 Zarządzanie przepisami
- **Własny przepiśnik**: Dodawaj własne dania ze zdjęciami, listą składników i opisem przygotowania. Przepisy mogą być publiczne lub prywatne.
- **Edycja danych**: Możliwość aktualizacji treści przepisów w dowolnym momencie.
- **Twoja kuchnia**: Lista produktów, które posiadasz – na jej podstawie aplikacja filtruje dostępne przepisy.

### 🗣️ Pomoc podczas gotowania
- **Bezdotykowe gotowanie**: Przełączaj kroki przepisu komendami (*"dalej"*, *"wróć"*, *"następny"*, *"poprzedni"*, *"potwórz"*, *"jeszcze raz"*) bez dotykania ekranu. Instrukcje czytane są na głos.
- **Liczenie porcji**: Wybierz liczbę porcji, a aplikacja sama przeliczy ilości potrzebnych składników.
- **Podpowiedzi o składnikach**: Przy każdym przepisie widzisz, które produkty masz w kuchni, a których Ci brakuje.

### 🔐 Bezpieczeństwo i synchronizacja
- **Logowanie przez Google**: Szybki i bezpieczny dostęp do aplikacji za pomocą konta Google.
- **Synchronizacja w chmurze**: Twoje przepisy i zawartość kuchni są bezpiecznie przechowywane w Firebase, dzięki czemu masz do nich dostęp na każdym urządzeniu.

---

## Wkrótce:

- **Integracja z AI**: Inteligentne wyszukiwanie przepisów.
- **Usprawnienia funkcjonalności aplikacji**

---

## Tech stack

- **Język**: Kotlin (2.3.21)
- **UI**: Jetpack Compose (Material 3)
- **Backend**: Firebase (Firestore, Authentication, Analytics)
- **Multimedia**: Cloudinary (Zarządzanie obrazami), Coil (Ładowanie obrazów)
- **Nawigacja**: Compose Navigation (2.9.8)
- Speech-to-Text (STT) & Text-to-Speech (TTS) dla asystenta gotowania
- FuzzyWuzzy Kotlin (Logika dopasowania składników)

---

## Instalacja i uruchomienie

1. **Sklonuj repozytorium**:
   ```
   git clone https://github.com/AleksZ28/WhatYummy-AI.git
   ```

2. **Konfiguracja Firebase**:
   - Dodaj plik `google-services.json` z Firebase do folderu `app/`.
   - Włącz Firestore Database i Authentication (Google Provider) in the Firebase console.

3. **Konfiguracja Cloudinary**:
   - Skonfiguruj `cloud_name` w klasie `WhatYummyApp.kt`.
   - Upewnij się, że masz ustawiony odpowiedni `upload_preset` w `AddRecipeViewModel`.

4. **Zbuduj i uruchom**:
   - Otwórz projekt w Android Studio.
   - Zsynchronizuj Gradle i uruchom na emulatorze lub fizycznym urządzeniu z min. SDK 26.

> **Ważne (Podpisywanie aplikacji)**: Projekt skonfigurowany jest pod konkretny plik `debug.keystore`. Jeśli go nie masz, wygeneruj go w folderze `app/signing/` poniższym poleceniem (hasło: `android`):
> ```bash
> keytool -genkey -v -keystore debug.keystore -storepass android -alias androiddebugkey -keypass android -keyalg RSA -keysize 2048 -validity 10000 -dname "CN=Android Debug,O=Android,C=US"
> ```
> Pamiętaj, aby dodać certyfikat SHA-1 wygenerowanego klucza do swojej konsoli Firebase, inaczej logowanie Google nie będzie działać.

---
