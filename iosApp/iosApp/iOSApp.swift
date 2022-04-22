import SwiftUI

@main
struct iOSApp: App {
	var body: some Scene {
		WindowGroup {
            NavigationView {
                HomeView()
                    .navigationTitle("TurnipOff")
                    .navigationBarTitleDisplayMode(.inline)
            }
		}
	}
}
