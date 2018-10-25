/*
 * Copyright (c) 2017, Salesforce.com, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.salesforce.op

import com.twitter.algebird.Tuple4Semigroup

package object filters {
  private[filters] type AllFeatures =
    (Map[FeatureKey, Seq[Double]], Map[FeatureKey, Seq[Double]], Map[FeatureKey, Seq[String]])
  private[filters] type AllSummaries = (
    Double,
    Map[FeatureKey, HistogramSummary],
    Map[FeatureKey, HistogramSummary],
    Map[FeatureKey, TextSummary]
  )
  private[filters] type FeatureKey = (String, Option[String])
  private[filters] type ProcessedSeq = Either[Seq[String], Seq[Double]]

  implicit val allSummariesSg = new Tuple4Semigroup[Double,
    Map[FeatureKey, HistogramSummary],
    Map[FeatureKey, HistogramSummary],
    Map[FeatureKey, TextSummary]]

  case class AllDistributions(
      responseDistributions: Map[FeatureKey, FeatureDistribution],
      numericDistributions: Map[FeatureKey, FeatureDistribution],
      textDistributions: Map[FeatureKey, FeatureDistribution]) {
    def predictorDistributions: Map[FeatureKey, FeatureDistribution] =
      numericDistributions ++ textDistributions

    def predictorKeySet: Set[FeatureKey] = predictorDistributions.keySet
  }
}
