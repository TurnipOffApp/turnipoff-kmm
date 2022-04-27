//
//  ImageAsync.swift
//  iosApp
//
//  Created by Jessy Bonnotte on 22/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct ImageAsync<Content: View>: View {

    private let url: URL?
    private let scale: CGFloat
    private let transaction: Transaction
    private let content: (ImageAsyncPhase) -> Content

    init(url: URL?, scale: CGFloat = 1) where Content == Image {
        self.url = url
        self.scale = scale
        self.transaction = Transaction()
        self.content = { $0.image ?? Image("") }
    }

    init<I, P>(url: URL?,
                      scale: CGFloat = 1,
                      @ViewBuilder content: @escaping (Image) -> I,
                      @ViewBuilder placeholder: @escaping () -> P) where Content == _ConditionalContent<I, P>, I : View, P : View {
        self.url = url
        self.scale = scale
        self.transaction = Transaction()
        self.content = { phase -> _ConditionalContent<I, P> in
            if let image = phase.image {
                return ViewBuilder.buildEither(first: content(image))
            } else {
                return ViewBuilder.buildEither(second: placeholder())
            }
        }
    }

    init(url: URL?,
                scale: CGFloat = 1,
                transaction: Transaction = Transaction(),
                @ViewBuilder content: @escaping (ImageAsyncPhase) -> Content) {
        self.url = url
        self.scale = scale
        self.transaction = transaction
        self.content = content
    }

    var body: some View {
        if #available(iOS 14.0, macOS 11.0, tvOS 14.0, watchOS 7.0, *) {
            ContentBody(url: url,
                        scale: scale,
                        transaction: transaction,
                        content: content)
        } else {
            ContentCompatBody(url: url,
                              scale: scale,
                              transaction: transaction,
                              content: content)
        }
    }
}

private final class Provider: ObservableObject {
    @Published var phase: ImageAsyncPhase

    init() {
        self.phase = .empty
    }

    func task(url: URL?,
              scale: CGFloat,
              transaction: Transaction) {
        guard let url = url else {
            return
        }
        URLSession.shared.dataTask(with: url) { data, _, error in
            DispatchQueue.main.async { [weak self] in
                if let error = error {
                    self?.phase = .failure(error)
                    return
                }

                withTransaction(transaction) {
                    self?.phase = self?.image(from: data, scale: scale)
                        .map{ ImageAsyncPhase.success($0) }
                        ?? .empty
                }
            }
        }
        .resume()
    }

    private func image(from data: Data?, scale: CGFloat) -> Image? {
        #if os(macOS)
        // TODO: Support scale on macOS
        return data
            .flatMap(NSImage.init(data:))
            .map(Image.init(nsImage:))
        #else
        return data
            .flatMap { UIImage(data: $0, scale: scale) }
            .map(Image.init(uiImage:))
        #endif
    }
}

@available(iOS 14.0, macOS 11.0, tvOS 14.0, watchOS 7.0, *)
private struct ContentBody<Content: View>: View {
    @StateObject private var provider = Provider()
    private let url: URL?
    private let scale: CGFloat
    private let transaction: Transaction
    private let content: (ImageAsyncPhase) -> Content

    init(url: URL?,
         scale: CGFloat,
         transaction: Transaction,
         @ViewBuilder content: @escaping (ImageAsyncPhase) -> Content) {
        self.url = url
        self.scale = scale
        self.transaction = transaction
        self.content = content
    }

    var body: some View {
        content(provider.phase)
            .onAppear {
                provider.task(url: url, scale: scale, transaction: transaction)
            }
            .onChange(of: url) { url in
                provider.task(url: url, scale: scale, transaction: transaction)
            }
    }
}

@available(iOS, deprecated: 14.0)
@available(macOS, deprecated: 11.0)
@available(tvOS, deprecated: 14.0)
@available(watchOS, deprecated: 7.0)
private struct ContentCompatBody<Content: View>: View {
    struct Body: View {
        @ObservedObject private var provider: Provider
        private let content: (ImageAsyncPhase) -> Content

        init(provider: Provider,
             url: URL?,
             scale: CGFloat,
             transaction: Transaction,
             @ViewBuilder content: @escaping (ImageAsyncPhase) -> Content) {
            self.provider = provider
            self.content = content
            self.provider.task(url: url, scale: scale, transaction: transaction)
        }

        var body: some View {
            content(provider.phase)
        }
    }

    @State private var provider = Provider()
    private let url: URL?
    private let scale: CGFloat
    private let transaction: Transaction
    private let content: (ImageAsyncPhase) -> Content

    init(url: URL?,
         scale: CGFloat,
         transaction: Transaction,
         @ViewBuilder content: @escaping (ImageAsyncPhase) -> Content) {
        self.url = url
        self.scale = scale
        self.transaction = transaction
        self.content = content
    }

    var body: Body {
        Body(provider: provider,
             url: url,
             scale: scale,
             transaction: transaction,
             content: content)
    }
}

struct ImageAsync_Previews: PreviewProvider {
    static var url: URL? {
        URL(string: "https://httpbin.org/image/png")
    }

    static var previews: some View {
        VStack {
            ImageAsync(url: url, scale: 2.0)
                .frame(width: 100, height: 100)

            ImageAsync(
                url: url,
                content: {
                    $0
                        .resizable()
                        .clipShape(Circle())
                },
                placeholder: {
                    Color.black
                }
            )
            .frame(width: 100, height: 100)

            ImageAsync(
                url: url,
                transaction: Transaction(animation: .linear),
                content: { phase in
                    if let image = phase.image {
                        image
                            .resizable()
                            .aspectRatio(contentMode: .fit)
                    } else if phase.error != nil {
                        Color.red
                    } else {
                        Color.blue
                    }
                }
            )
            .frame(width: 100, height: 100)
        }
    }
}
