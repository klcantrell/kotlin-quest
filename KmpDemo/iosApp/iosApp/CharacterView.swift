import SwiftUI
import Common

struct CharacterView: View {
    let state: SwApiUiState
    let character: Character?
    let onAppear: () -> Void
    
    var body: some View {
        VStack {
            switch state {
            case .fetchingNewCharacter(let characterId):
                if character?.id != nil && character!.id != characterId {
                    CharacterCard(character: character!)
                } else {
                    ProgressView()
                        .progressViewStyle(CircularProgressViewStyle(tint: .blue))
                        .scaleEffect(2)
                }
            case .characterLoaded:
                if character == nil {
                    Text("Something went wrong loading this character. Please try again later.")
                        .padding(.horizontal, 64)
                        .multilineTextAlignment(.center)
                } else {
                    CharacterCard(character: character!)
                }
            default:
                Text("Something went wrong loading the app. Please try again later.")
                    .padding(.horizontal, 64)
                    .multilineTextAlignment(.center)
            }
        }
        .onAppear { onAppear() }
    }
}

struct CharacterCard: View {
    let character: Character
    
    func cacheAndRender(image: Image, url: URL) -> some View {
        ImageCache.cacheImage(url: url)
        return image
            .resizable()
            .scaledToFill()
    }
    
    var body: some View {
        if #available(iOS 15.0, *) {
            let url = URL(string: "https://res.cloudinary.com/kalalau/kmpdemo/\(character.id).jpg")!
            
            VStack {
                if let cachedImage = ImageCache[url] {
                    Image(uiImage: cachedImage)
                        .resizable()
                        .scaledToFill()
                        .frame(width: 300, height: 300, alignment: .center)
                        .cornerRadius(10)
                } else {
                    AsyncImage(url: url, transaction: Transaction(animation: .easeInOut)) { phase in
                        switch phase {
                        case .success(let image):
                            cacheAndRender(image: image, url: url)
                        case .failure(_):
                            cacheAndRender(image: Image("empire-emblem"), url: url)
                        default:
                            Color.gray
                        }
                    }
                    .frame(width: 300, height: 300, alignment: .center)
                    .cornerRadius(10)
                }
                
                Spacer()
                    .frame(height: 10)
                
                Text(character.name)
                
                Spacer()
                    .frame(height: 10)
                
                Text("Appears in: \(character.appearsIn.joined(separator: ", "))")
                    .padding(.horizontal, 32)
            }
        } else {
            VStack {
                Image("empire-emblem")
                    .resizable()
                    .scaledToFill()
                
                Spacer()
                    .frame(height: 10)
                
                Text("Please upgrade to iOS 15")
            }
        }
    }
}

fileprivate class ImageCache {
    static private let cache = NSCache<NSString, UIImage>()
    
    static func cacheImage(url: URL) {
        let dataTask = URLSession.shared.dataTask(with: url) { data, _, error in
            guard let data = data, error == nil else {
                return
            }
            ImageCache.cache.setObject(UIImage(data: data) ?? UIImage(named: "empire-emblem")!, forKey: url.absoluteString as NSString)
        }
        dataTask.resume()
    }
    
    static subscript(url: URL) -> UIImage? {
        get {
            ImageCache.cache.object(forKey: url.absoluteString as NSString)
        }
    }
}
