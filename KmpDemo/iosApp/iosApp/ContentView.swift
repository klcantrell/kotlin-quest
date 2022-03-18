import SwiftUI
import Common

struct ContentView: View {
    @StateObject var viewModel = SwApiViewModel()
    
    var body: some View {
        switch viewModel.state {
        case .idle, .loading: Text("Loading...")
        case .error: Text("Ah, something went wrong. Try again later.")
        case .fetchingNewCharacter, .loaded:
            TabView {
                ForEach(1..<viewModel.characterCount) { id in
                    CharacterView(characterId: String(id))
                        .onAppear {
                            viewModel.loadCharacter(String(id))
                        }
                        .environmentObject(viewModel)
                }
            }
            .tabViewStyle(.page)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
