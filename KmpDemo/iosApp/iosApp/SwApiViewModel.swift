import Foundation
import Common

@MainActor
class SwApiViewModel: ObservableObject {
    @Published var state = SwApiUiState.idle
    
    private var characterCount: Int32 = 0
    private var characterData: [String : Character] = [:]
    private var loadingCharacter = false
    
    private let repository = SwApiRepository()
    
    init() {
        state = SwApiUiState.loading
        repository.getInitialData { result, error in
            if let swapiData = result {
                self.characterCount = swapiData.characterCount
                self.characterData = swapiData.characterData
                self.state = SwApiUiState.success(swapiData)
            }
        }
    }
    
    func loadCharacter(_ characterId: String) {
        if !loadingCharacter {
            loadingCharacter = true
            if self.characterData[characterId] == nil && !state.isLoading {
                print("fetching \(characterId)")
                state = SwApiUiState.fetchingNewCharacter(
                    SwapiData(
                        characterCount: self.characterCount,
                        characterData: self.characterData
                    )
                )
                repository.getCharacterById(characterId: characterId) { result, error in
                    if error == nil, let character = result {
                        self.characterData[character.id] = character
                        self.state = SwApiUiState.success(
                            SwapiData(
                                characterCount: self.characterCount,
                                characterData: self.characterData
                            )
                        )
                    } else {
                        self.characterData[characterId] = Character(
                            id: characterId,
                            name: "Could not load this character, please try again later",
                            appearsIn: []
                        )
                        self.state = SwApiUiState.success(
                            SwapiData(
                                characterCount: self.characterCount,
                                characterData: self.characterData
                            )
                        )
                    }
                }
            }
            loadingCharacter = false
        }
    }
}

enum SwApiUiState {
    case idle
    case loading
    case fetchingNewCharacter(SwapiData)
    case success(SwapiData)
    
    var isLoading: Bool {
        switch self {
        case .loading, .fetchingNewCharacter(_): return true
        default: return false
        }
    }
}
