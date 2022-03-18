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
    
    private var loadingCharacter = false
    
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
        if !loadingCharacter {
            loadingCharacter = true
            if swapiService.getCharacterById(characterId: characterId) == nil && !state.isLoading {
                state = .fetchingNewCharacter
                swapiService.loadCharacterById(characterId: characterId) { result, error in
                    if result != nil && error == nil {
                        self.state = .loaded
                    } else {
                        self.state = .error
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
    case fetchingNewCharacter
    case loaded
    case error
    
    var isLoading: Bool {
        switch self {
        case .loading, .fetchingNewCharacter: return true
        default: return false
        }
    }
}
