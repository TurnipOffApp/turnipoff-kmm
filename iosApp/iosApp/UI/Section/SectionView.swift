//
//  SectionView.swift
//  iosApp
//
//  Created by Jessy Bonnotte on 22/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct SectionView: View {

    @StateObject var viewModel: SectionViewModel

    var body: some View {
        VStack {
            let title = ["Worst", viewModel.config.category.title, "movies"].joined(separator: " ")
            Text(title)
            ScrollView(.horizontal) {
                LazyHStack {
                    ForEach(viewModel.movies) { movie in
                        let url = URL(string: PictureSizes.Poster.w342.buildURL(path: movie.posterPath ?? ""))
                        //NavigationLink(destination: MovieView(viewModel: .init(id: movie.id))) {
                            ImageURLRounded(url: url, contentMode: .fit)
                                .padding()
                                .onAppear {
                                    viewModel.loadMoreMoviesIfNeeded(currentMovie: movie)
                                }
                        //}
                    }
                }
            }
        }
        .onAppear {
            Task {
                await viewModel.getMovies()
            }
        }
    }

}

extension MovieSearchResult: Identifiable {
    
}
