import SwiftUI
import shared

@main
struct iOSApp: App {
    
    init() {
        Main_iosKt.doInitKoin()
    }
    
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
