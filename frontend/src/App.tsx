import { useState, useEffect } from 'react'
import { fetchSports, fetchScores, submitScore } from './api'
import type { Sport, Score } from './api'
import './App.css'

function App() {
  const [sports, setSports] = useState<Sport[]>([])
  const [scores, setScores] = useState<Score[]>([])
  const [selectedSport, setSelectedSport] = useState('')
  const [result, setResult] = useState('')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const [lastSubmitted, setLastSubmitted] = useState<{ sport: string; points: number } | null>(null)

  useEffect(() => {
    fetchSports().then(setSports)
    fetchScores().then(setScores)
  }, [])

  useEffect(() => {
    if (sports.length > 0 && !selectedSport) {
      setSelectedSport(sports[0].displayName)
    }
  }, [sports, selectedSport])

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError('')
    setLoading(true)

    try {
      const response = await submitScore(selectedSport, parseFloat(result))
      setLastSubmitted({ sport: response.sport, points: response.points })
      setResult('')
      const updatedScores = await fetchScores()
      setScores(updatedScores)
    } catch {
      setError('Failed to submit score. Check your input.')
    } finally {
      setLoading(false)
    }
  }

  const selectedSportData = sports.find(s => s.displayName === selectedSport)
  const isTrackEvent = selectedSportData?.eventType === 'TRACK'

  return (
    <div className="container">
      <h1>Decathlon Scorer</h1>

      <form onSubmit={handleSubmit} className="form">
        <div className="form-group">
          <label htmlFor="sport">Sport</label>
          <select
            id="sport"
            value={selectedSport}
            onChange={(e) => setSelectedSport(e.target.value)}
          >
            {sports.map((sport) => (
              <option key={sport.name} value={sport.displayName}>
                {sport.displayName} ({sport.eventType})
              </option>
            ))}
          </select>
        </div>

        <div className="form-group">
          <label htmlFor="result">
            Result ({isTrackEvent ? 'seconds' : 'meters'})
          </label>
          <input
            id="result"
            type="number"
            step="0.01"
            value={result}
            onChange={(e) => setResult(e.target.value)}
            placeholder={isTrackEvent ? 'e.g. 10.5' : 'e.g. 7.5'}
            required
          />
        </div>

        <button type="submit" disabled={loading}>
          {loading ? 'Submitting...' : 'Submit Score'}
        </button>

        {error && <p className="error">{error}</p>}
        {lastSubmitted && (
          <p className="success">
            Submitted {lastSubmitted.sport}: {lastSubmitted.points} points
          </p>
        )}
      </form>

      <h2>Recent Scores</h2>
      <table className="scores-table">
        <thead>
          <tr>
            <th>Sport</th>
            <th>Result</th>
            <th>Points</th>
            <th>Date</th>
          </tr>
        </thead>
        <tbody>
          {scores.map((score) => (
            <tr key={score.id}>
              <td>{score.sport}</td>
              <td>{score.result}</td>
              <td>{score.points}</td>
              <td>{new Date(score.createdAt).toLocaleString()}</td>
            </tr>
          ))}
          {scores.length === 0 && (
            <tr>
              <td colSpan={4} className="empty">No scores yet</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  )
}

export default App
