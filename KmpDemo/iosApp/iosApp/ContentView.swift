import SwiftUI
import Common

struct ContentView: View {
    @StateObject var viewModel = SwApiViewModel()
    
    var body: some View {
        switch viewModel.state {
        case .idle, .loading: Text("Loading...")
        case .fetchingNewCharacter(let swapiData), .success(let swapiData):
//            HStack {
//                Button("Previous") {
//                    viewModel.loadCharacter(
//                        String(
//                            (Int(viewModel.currentCharacterId) ?? 0) - 1
//                        )
//                    )
//                }
//                .disabled((Int(viewModel.currentCharacterId) ?? 0) <= 1)
//
//                CharacterView()
//                    .environmentObject(viewModel)
//
//                Button("Next") {
//                    viewModel.loadCharacter(
//                        String(
//                            (Int(viewModel.currentCharacterId) ?? 0) + 1
//                        )
//                    )
//                }
//                .disabled((Int(viewModel.currentCharacterId) ?? 0) >= swapiData.characterCount)
//            }
            TabView {
                ForEach(1..<Int(swapiData.characterCount)) { id in
                    CharacterView(characterId: String(id))
                        .onAppear {
                            print("onAppear for \(id)")
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
