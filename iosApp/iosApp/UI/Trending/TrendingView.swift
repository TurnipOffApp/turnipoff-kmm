//
//  TrendingView.swift
//  iosApp
//
//  Created by Jessy Bonnotte on 22/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TrendingView: View {

    @StateObject private var viewModel = TrendingViewModel()
    
    var body: some View {
        CarouselView(
            pages: viewModel.movies.map { TrendingMovieView(movie: $0) },
            indexDisplayMode: .always
        )
            .onAppear {
                Task {
                    await viewModel.getTrendingList()
                }
            }
    }
}

struct TrendingView_Previews: PreviewProvider {
    static var previews: some View {
        TrendingView()
    }
}

struct TrendingMovieView: View {

    let movie: MovieSearchResult

    var body: some View {
        let url = URL(string: PictureSizes.Poster.w342.buildURL(path: movie.posterPath ?? ""))
        //NavigationLink(destination: MovieView(viewModel: .init(id: movie.id))) {
            ImageURLRounded(url: url, contentMode: .fit)
        //}
    }

}
