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
    
    func characterFailedToFetch(_ characterId: String) -> Bool {
        return swapiService.characterFailedToFetch(characterId: characterId)
    }
    
    private let swapiService = SwApiService()
    
    init() {
        state = SwApiUiState.initializing
        swapiService.loadInitialData { result, error in
            if result != nil && error == nil  {
                self.state = .characterLoaded
            } else {
                self.state = .initializationError
            }
        }
    }
    
    func loadCharacter(_ characterId: String) {
        if getCharacter(characterId) != nil {
            return
        }
        if characterFailedToFetch(characterId) {
            return
        }
        
        if !state.isBusy {
            state = .fetchingNewCharacter(characterId)
            swapiService.loadCharacterById(characterId: characterId) { result, _ in
                self.state = .characterLoaded
            }
        }
    }
    
}

enum SwApiUiState {
    case idle
    case initializing
    case fetchingNewCharacter(String)
    case characterLoaded
    case initializationError
    
    var isBusy: Bool {
        switch self {
        case .fetchingNewCharacter: return true
        default: return false
        }
    }
}
