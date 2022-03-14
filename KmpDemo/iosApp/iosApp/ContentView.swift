import SwiftUI
import Common

struct ContentView: View {
    @StateObject var viewModel = SwApiViewModel()

	var body: some View {
        switch(viewModel.state) {
        case .idle, .loading: Text("Loading...")
        case .success(let character): Text(character.name)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
