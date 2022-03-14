import Foundation
import Common

@MainActor
class SwApiViewModel: ObservableObject {
    @Published var state = SwApiUiState.idle

    private let repository = SwApiRepository()

    init() {
        state = SwApiUiState.loading
        repository.getCharacterById(characterId: "1") { result, error in
            if let character = result {
                self.state = SwApiUiState.success(character)
            }
        }
    }
}

enum SwApiUiState {
    case idle
    case loading
    case success(ApiCharacter)
}
