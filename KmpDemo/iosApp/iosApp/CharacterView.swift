import SwiftUI

struct CharacterView: View {
    @EnvironmentObject var viewModel: SwApiViewModel

    let characterId: String

    var body: some View {
        HStack {
            switch viewModel.state {
            case .idle, .loading, .fetchingNewCharacter(_) : Text("Loading next character...")
            case .success(let swapiData):
                Text(swapiData.characterData[characterId]?.name ?? "Could not load this character, try again later.")
            }
        }
    }
}
