//
//  CreditView.swift
//  iosApp
//
//  Created by Jessy Bonnotte on 27/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct CreditView: View {

    // MARK: Properties

    let credit: MovieCredits.Credit

    // MARK: View

    var body: some View {
        VStack(alignment: .center) {
            NavigationLink(destination: self.destination) {
                MoviePosterImage(
                    url: URL(string: PictureSizes.Poster.w342.buildURL(path: credit.profilePath ?? ""))
                )
            }

            Text(credit.title)
                .font(.subheadline)
            Text(credit.subtitle)
                .font(.caption)
            Spacer()
        }
    }

    @ViewBuilder
    private var destination: some View {
        MovieView(viewModel: .init(id: credit.id))
        /*switch credit.type {
        case .movie:
            MovieView(viewModel: .init(id: credit.id))
        case .person:
            break
            //PersonView(viewModel: .init(id: credit.id))
        }*/
    }

}

