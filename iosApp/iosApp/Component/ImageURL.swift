//
//  ImageURL.swift
//  iosApp
//
//  Created by Jessy Bonnotte on 22/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct ImageURL: View {

    let url: URL?
    let contentMode: ContentMode

    init(url: URL?, contentMode: ContentMode = .fill) {
        self.url = url
        self.contentMode = contentMode
    }

    var body: some View {
        ImageAsync(
            url: url,
            transaction: .init(animation: .linear),
            content: { phase in
                self.image(from: phase)
                    .resizable()
                    .aspectRatio(contentMode: contentMode)
            }
        )
    }

}

private extension ImageURL {

    func image(from phase: ImageAsyncPhase) -> Image {
        let imagee: Image
        switch phase {
        case .success(let image):
            imagee = image
        case .failure, .empty:
            imagee = Image("missing_picture")
        }
        return imagee
    }
}
