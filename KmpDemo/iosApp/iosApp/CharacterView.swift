import SwiftUI
import Common

struct CharacterView: View {
    let state: SwApiUiState
    let character: Character?
    let onAppear: () -> Void
    
    var body: some View {
        HStack {
            switch state {
            case .idle, .loading, .fetchingNewCharacter : Text("Loading next character...")
            case .error: Text("Ah, something went wrong. Try again later.")
            case .loaded:
                Text(character?.name ?? "Could not load this character, try again later.")
            }
        }
        .onAppear { onAppear() }
    }
}
