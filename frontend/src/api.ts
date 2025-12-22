export interface Sport {
  name: string;
  displayName: string;
  eventType: string;
}

export interface Score {
  id: string;
  sport: string;
  result: number;
  points: number;
  createdAt: string;
}

export interface SubmitResponse {
  id: string;
  sport: string;
  result: number;
  points: number;
}

export async function fetchSports(): Promise<Sport[]> {
  const response = await fetch('/sport/list', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
  });
  const data = await response.json();
  return data.content;
}

export async function fetchScores(): Promise<Score[]> {
  const response = await fetch('/score/list', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
  });
  const data = await response.json();
  return data.content;
}

export async function submitScore(sport: string, result: number): Promise<SubmitResponse> {
  const response = await fetch('/score/submit', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ sport, result }),
  });
  if (!response.ok) {
    throw new Error('Validation failed');
  }
  return response.json();
}
