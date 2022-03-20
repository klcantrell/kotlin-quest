import Foundation
import Common

@MainActor
class SwApiViewModel: ObservableObject {
    @Published var state = SwApiUiState.idle
    
    var characterCount: Int {
        return Int(swapiService.getCharacterCount())
    }
    
    func getCharacter(_ characterId: String) -> Character? {
        return swapiService.getCharacterById(characterId: characterId)
    }
    
    private let swapiService = SwApiService()
    
    init() {
        state = SwApiUiState.loading
        swapiService.loadInitialData { result, error in
            if result != nil && error == nil  {
                self.state = .loaded
            } else {
                self.state = .error
            }
        }
    }
    
    func loadCharacter(_ characterId: String) {
        if getCharacter(characterId) != nil {
            return
        }
        if !state.isLoading {
            state = .fetchingNewCharacter(characterId)
            swapiService.loadCharacterById(characterId: characterId) { result, error in
                if result != nil && error == nil {
                    self.state = .loaded
                } else {
                    self.state = .error
                }
            }
        }
    }
}

enum SwApiUiState {
    case idle
    case loading
    case fetchingNewCharacter(String)
    case loaded
    case error
    
    var isLoading: Bool {
        switch self {
        case .loading, .fetchingNewCharacter: return true
        default: return false
        }
    }
}
