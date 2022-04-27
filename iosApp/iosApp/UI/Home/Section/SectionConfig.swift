//
//  SectionConfig.swift
//  iosApp
//
//  Created by Jessy Bonnotte on 22/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

struct SectionConfig {

    let category: Category
    let releaseDate: ClosedRange<Foundation.Date>?

}

extension SectionConfig {

    enum Category: String, Identifiable, CaseIterable {
        case action
        case nineties
        case eighties
        case comedy

        var id: Self { self }

        var genres: [TheMovieDBMovieGenre]? {
            switch self {
            case .action:
                return [.action]
            case .nineties, .eighties:
                return nil
            case .comedy:
                return [.comedy]
            }
        }

        var title: String {
            switch self {
            case .action:
                return "action"
            case .nineties:
                return "90's"
            case .eighties:
                return "80's"
            case .comedy:
                return "comedy"
            }
        }

    }

}

extension SectionConfig {

    private static var dateFormatter: DateFormatter = {
        let aoDateFormatter = DateFormatter()
        aoDateFormatter.dateFormat = "yyyy-MM-dd"
        return aoDateFormatter
    }()


    static func builder(category: Category) -> Self {
        switch category {
        case .action, .comedy:
            return .init(category: category, releaseDate: nil)
        case .nineties:
            let lowerBound = Self.dateFormatter.date(from: "1990-01-01")!
            let upperBound = Self.dateFormatter.date(from: "1999-12-31")!
            return .init(category: category, releaseDate: lowerBound...upperBound)
        case .eighties:
            let lowerBound = Self.dateFormatter.date(from: "1980-01-01")!
            let upperBound = Self.dateFormatter.date(from: "1989-12-31")!
            return .init(category: category, releaseDate: lowerBound...upperBound)
        }
    }

}
