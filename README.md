# Turnipoff Kotlin Multiplatform Mobile



## Configuration

### Android

- [ ] [Android Studio Bumblebee | 2021.1.1 Patch 3](https://developer.android.com/studio/#downloads)
- [ ] Kotlin version 1.5.10
- [ ] Compose version  1.1.1

### iOS

- [ ] Cocoapods

### Shared

- [ ] Serialization =ersion 1.2.2
- [ ] Ktor version 1.6.1

## Integration

### iOS

Install the CocoaPods dependency manager:
```
$ sudo gem install cocoapods
```
Install the cocoapods-generate plugin:
```
$ sudo gem install cocoapods-generate
```

Cocoapods is already configured in shared module
```
plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
}

kotlin {
    
    ...
    
    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
        }
    }
    
    ...
}
```

### Shared

We will use the following multiplatform libraries in our project:
- kotlinx.coroutines – we will use coroutines for asynchronous code.
- Ktor – this will be our HTTP client for retrieving data over the internet.
- kotlinx.serialization – to deserialize JSON responses into objects of entity classes.
To add a multiplatform library to the KMM module, we need to add dependency instructions (in our case, implementation) to the dependencies block of the relevant source sets in the 'build.gradle.kts' file in the KMM module's directory.
See more about adding dependencies [here](https://play.kotlinlang.org/hands-on/Networking%20and%20Data%20Storage%20with%20Kotlin%20Multiplatfrom%20Mobile/03_Adding_dependecies)
