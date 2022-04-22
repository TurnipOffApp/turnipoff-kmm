//
//  ImageAsyncPhase.swift
//  iosApp
//
//  Created by Jessy Bonnotte on 22/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

public enum ImageAsyncPhase {
    case empty
    case success(Image)
    case failure(Error)

    public var image: Image? {
        switch self {
        case .empty, .failure:
            return nil
        case .success(let image):
            return image
        }
    }

    public var error: Error? {
        switch self {
        case .empty, .success:
            return nil
        case .failure(let error):
            return error
        }
    }
}
