//
//  SectionViewModel.swift
//  iosApp
//
//  Created by Jessy Bonnotte on 22/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import Combine
import shared

class SectionViewModel: ObservableObject {
    
    private var subscriptions = Set<AnyCancellable>()
    private var page: Int = 0
    private var pageMax: Int = 1

    @Published private(set) var isLoadingPage = false
    @Published private(set) var movies: [MovieSearchResult]

    let config: SectionConfig

    init(config: SectionConfig) {
        self.config = config
        self.movies = []
    }
    
    @MainActor
    func getMovies() async {
        do {
            let result: TheMovieDBResponse<MovieSearchResult> = try await Service
                .shared
                .client
                .discover(
                    sortby: "vote_average.asc",
                    voteAverage: "1",
                    page: Int32(page) + 1,
                    genres: config.category.genres,
                    releaseAfter: "",
                    releaseBefore: ""
                )
        
            movies = result.results as? [MovieSearchResult] ?? []
        } catch {
            movies = []
        }
    }

    /*func getMovies() {
        guard !isLoadingPage && page < pageMax else {
            return
        }

        isLoadingPage = true

        Service
            .shared
            .client
            .discover(
                sortBy: "vote_average.asc",
                voteAverage: "1",
                page: page + 1,
                genres: config.category.genres,
                releaseAfter: config.releaseDate?.lowerBound,
                releaseAfter: config.releaseDate?.upperBound
            )
            .receive(on: RunLoop.main)
            .handleEvents(receiveOutput: { moviesResult in
                self.page = moviesResult.page
                self.pageMax = moviesResult.totalPages
                self.isLoadingPage = false
            })
            .map({ moviesResult in
                return self.movies + moviesResult.results
            })
            .catch({ _ in Just(self.movies) })
            .sink { movies in
                self.movies = movies
            }
            .store(in: &subscriptions)
    }*/

    func loadMoreMoviesIfNeeded(currentMovie movie: MovieSearchResult) {
        let thresholdIndex = movies.index(movies.endIndex, offsetBy: -5)
        if movies.firstIndex(where: { $0.id == movie.id }) == thresholdIndex {
            Task {
                await getMovies()
            }
        }
      }
}
