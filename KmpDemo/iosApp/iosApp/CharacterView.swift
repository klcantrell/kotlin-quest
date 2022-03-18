import SwiftUI

struct CharacterView: View {
    @EnvironmentObject var viewModel: SwApiViewModel
    
    let characterId: String
    
    var body: some View {
        HStack {
            switch viewModel.state {
            case .idle, .loading, .fetchingNewCharacter : Text("Loading next character...")
            case .error: Text("Ah, something went wrong. Try again later.")
            case .loaded:
                Text(viewModel.getCharacter(characterId)?.name ?? "Could not load this character, try again later.")
            }
        }
    }
}
