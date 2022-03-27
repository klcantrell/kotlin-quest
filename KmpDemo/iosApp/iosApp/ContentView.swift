import SwiftUI
import Common

struct ContentView: View {
    @StateObject var viewModel = SwApiViewModel()
    @State var selection = 1
    
    var body: some View {
        switch viewModel.state {
        case .idle, .initializing:
            ProgressView()
                .progressViewStyle(CircularProgressViewStyle(tint: .blue))
                .scaleEffect(2)
        case .initializationError:
            Text("Something went wrong loading the app. Please try again later.")
                .padding(.horizontal, 64)
                .multilineTextAlignment(.center)
        default:
            TabView(selection: $selection) {
                ForEach(1..<viewModel.characterCount) { id in
                    CharacterView(
                        state: viewModel.state,
                        character: viewModel.getCharacter(String(id)),
                        onAppear: {
                            viewModel.loadCharacter(String(id))
                        }
                    ).tag(id)
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
